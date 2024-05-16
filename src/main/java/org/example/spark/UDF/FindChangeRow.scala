package org.example.spark.UDF

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

object FindChangeRow {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("FindChangeRow")
    val spark = SparkSession.builder().config(conf).getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")
    val df: DataFrame = spark.read.option("header",true).csv("file:///BIgdate\\bigdateinfo\\spark\\软件\\data\\demo\\diff_row.csv")
    //注册为临时视图
    df.createTempView("temp")
    //为每行数据添加一列row_number
    spark.sql(
      """
        |select id,change,name,row_number()over(partition by id order by name) as rn
        |from temp
        |""".stripMargin).createTempView("tb1")
    spark.sql(
      """
        |select a.id,a.change,a.name from tb1 a join tb1 b on a.id = b.id
        |where a.change != b.change
        |and a.rn  = b.rn -1
        |""".stripMargin).show()
    spark.close()
  }
}
