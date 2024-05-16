package org.example.spark.UDF

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

object ReadJsonArrayFile {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("ReadJsonArrayFile")
    val spark = SparkSession.builder().config(conf).getOrCreate()
    spark.sparkContext.setLogLevel("ERROR")
    val frame: DataFrame = spark.read.json("file:///BIgdate\\bigdateinfo\\spark\\软件\\data\\demo\\jsonArrayFile.json")
    frame.printSchema()
    frame.show(false)
    frame.createOrReplaceTempView("temp")
    spark.sql(
      """
        |select name,age,el.xueqi,el.yuwen,el.shuxue,el.yingyu
        |from (select name,age,explode(scores) as el from temp)
        |""".stripMargin).show()
    ////方式二:explode和col函数
    import org.apache.spark.sql.functions._
    import spark.implicits._
    val df1:DataFrame = frame.select(frame.col("name"),frame.col("age"),explode(frame.col("scores")).as("el"))
    df1.select('name,$"age",col("el.xueqi"),$"el.yuwen",$"el.shuxue",$"el.yingyu").show(false)
    spark.close()
  }
}
