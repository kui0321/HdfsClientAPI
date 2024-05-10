package org.example.spark.DataFrame

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

object DataFrameAndDataSet {
  def main(args: Array[String]): Unit = {
    //1.创建spark配置文件对象
    val conf = new SparkConf().setMaster("local").setAppName("DataFrameAndDataSet")
    //2.创建spark执行入口对象
    val spark = new SparkSession.Builder().config(conf).getOrCreate()
    //4.添加隐式转换
    import spark.implicits._
    //5.读取文件并使用样例类创建RDD对象
    val rdd:RDD[Student] = spark.sparkContext.textFile("file:///BIgdate/bigdateinfo/spark/软件/data/sql/student.txt")
      .map(_.split(","))
      .map(arr=>Student(arr(0).toInt,arr(1),arr(2).toInt))
    //B1.RDD->DataFrame
    val df:DataFrame = rdd.toDF()
    //B2.DataFrame->DataSet
    val ds:Dataset[Student] = df.as[Student]
    println(ds)
    //B3.DataSet->DataFrame
    val df1:DataFrame = ds.toDF()
    println(df1)
    //B4.RDD->DataSet
    val ds1:Dataset[Student] = rdd.toDS()
    println(ds1)
    //B5.DataSet->RDD
    val rdd1: RDD[Student] = ds.rdd
    //B6.DataFrame->RDD
    val rdd2: RDD[Row] = df.rdd
    //3.关闭spark
    spark.stop()
  }
}
