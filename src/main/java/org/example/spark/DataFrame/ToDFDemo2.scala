package org.example.spark.DataFrame

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}

object ToDFDemo2 {
  def main(args: Array[String]): Unit = {
    //1.创建配置文件对象
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("ToDFDemo2")
    //2.创建SparkSession对象
    val spark:SparkSession = SparkSession.builder().config(conf).getOrCreate()
    //4.添加隐式转换
    import spark.implicits._
    //5.读取本地文件，并创建RDD[Student]
    val rdd:RDD[Student] = spark.sparkContext.textFile("file:///BIgdate/bigdateinfo/spark/软件/data/sql/student.txt")
      .map(_.split(","))
      .map(arr=>Student(arr(0).toInt,arr(1).trim,arr(2).toInt))
    //6.通过rdd.toDF()转换为DataFrame
    val df:DataFrame = rdd.toDF()
    //7.输出df的结构信息和数据信息
    df.printSchema()
    df.show()
    //3.关闭spark
    spark.stop()
  }
}
