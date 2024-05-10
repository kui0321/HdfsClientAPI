package org.example.spark.SSD

import org.apache.spark.SparkConf
import org.apache.spark.sql.{Column, DataFrame, SparkSession}

object SSDslApi {
  def main(args: Array[String]): Unit = {
    val conf:SparkConf = new SparkConf().setAppName("SSDslApi").setMaster("local[*]")
    val spark:SparkSession = SparkSession.builder().config(conf).getOrCreate()
    import spark.implicits._
    val df:DataFrame = spark.read.format("csv")
      .option("header",true)
      .option("delimiter",",")
      .option("inferSchema",true)
      .option("encoding","UTF-8")
      .load("file:///BIgdate/bigdateinfo/spark/软件/data/sql/score.csv")
    println("-----------获取指定的列----------")
    df.select($"id",$"score"+1).show(5)
    df.select('id,'score+1).show(5)
    //获取列的Column对象
    val idcolumn:Column = df("id")
    val scorecolumn:Column = df("score")
    df.select(idcolumn,scorecolumn).show(5)
    println("-------------------filter过滤数据----------------")
    df.filter($"score" > 60).show(5)
    df.filter(df("score") > 60).show(5)
    df.filter($"score" > 60 and $"score" < 80).show()
    println("-------------------where过滤数据-------------------")
    df.where($"score" > 60).show()
    df.where(df("score") > 60 ).show()
    df.where($"score" > 60 and $"score" < 80).show()
    println("-------------------groupby显示数据-------------------")
    val groupbyData = df.groupBy("project")
    val dataFrame:DataFrame = groupbyData.count()
    dataFrame.show()
    groupbyData.max("score").show()
    df.show()
    spark.close()
  }
}
