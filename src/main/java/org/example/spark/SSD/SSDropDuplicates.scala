package org.example.spark.SSD

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

object SSDropDuplicates {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("SSDropDuplicates")
    val spark = SparkSession.builder().config(conf).getOrCreate()
    val df:DataFrame = spark.read.format("csv")
      .option("header","true")
      .option("delimiter",",")
      .option("inferSchema","true")
      .option("encoding","UTF-8")
      .load("file:///BIgdate/bigdateinfo/spark/软件/data/sql/clear_data.csv")
    df.printSchema()
    df.show()
    //无参数去重，将所有列联合起来进行比较，只保留一条(第一条)
    df.dropDuplicates().show()
    //有参数去重，指定字段进行去重
    df.dropDuplicates("name").show()
    spark.close()
  }
}
