package org.example.spark.UDF

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object RowColumnTransfer2 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("RowColumnTransfer2")
    val spark = SparkSession.builder().config(conf).getOrCreate()
    spark.sparkContext.setLogLevel("ERROR")
    val frame = spark.read.option("header","true").csv("file:///BIgdate\\bigdateinfo\\spark\\软件\\data\\demo\\rowcolumn.csv")
    frame.createTempView("temp1")
    spark.sql(
      """
        |select username,concat_ws("#",collect_list(concat(item,",",price))) as cw
        |from temp1
        |group by username
        |""".stripMargin).createTempView("temp2")
    spark.sql(
      """
        |select username,str_to_map(cw,"#",",") as mp
        |from temp2
        |""".stripMargin).createTempView("temp3")
    spark.sql(
      """
        |select username,mp['A'] as A,mp['B'] as B,mp['C'] as C,mp['D'] as D
        |from temp3
        |""".stripMargin).show()
    spark.sql(
      """
        |select username,mp['A'] as A,mp['B'] as B,mp['C'] as C,mp['D'] as D
        |from temp3
        |""".stripMargin).createTempView("temp4")
    spark.sql(
      """
        |select username,item,price
        |from (select username,explode(map("A",A,"B",B,"C",C,"D",D)) as (item,price)
        |from temp4) temp5
        |where price is not null
        |""".stripMargin).show(100,false)
    spark.sql("select * from temp1").show()
    spark.close()
  }
}
