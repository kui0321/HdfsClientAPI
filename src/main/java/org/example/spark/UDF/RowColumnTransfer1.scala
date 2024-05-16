package org.example.spark.UDF

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

object RowColumnTransfer1{
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("RowColumnTransfer1")
    val spark = SparkSession.builder().config(conf).getOrCreate()
    spark.sparkContext.setLogLevel("ERROR")
    val df:DataFrame = spark.read.option("header","true").csv("file:///BIgdate\\bigdateinfo\\spark\\软件\\data\\demo\\rowcolumn.csv")
    df.createTempView("temp1")
    spark.sql(
      """
        |select
        |username,concat_ws(",",collect_list(item)) item,sum(price) totalprice
        |from temp1
        |group by username
        |""".stripMargin).show(false)
    //.扩展：行转列，暂时不考虑总价格拆分问题
    spark.sql(
      """
        |select username,concat_ws(",",collect_list(item)) cw
        |,sum(price) totalprice
        |from temp1
        |group by username
        |""".stripMargin).createTempView("temp2")

    spark.sql(
      """
        |select username,explode(split(cw,",")) item,totalprice
        |from temp2
        |""".stripMargin).show()
    spark.close()
  }
}
