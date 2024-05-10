package org.example.spark.DataFrame

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

object DataFrameToRdd {
  def main(args: Array[String]): Unit = {
    //1创建上下文环境配置对象
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("DataFrameToRdd")
    //2.创建执行环境入口对象SparkSession对象
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()
    //4.添加spark隐式转换
    import spark.implicits._
    //3.读取本地文本文件，映射并创建RDD[Row]对象
    val rdd: RDD[Student] = spark.sparkContext.textFile("file:///BIgdate/bigdateinfo/spark/软件/data/sql/student.txt")
      .map(_.split(","))
      .map(arr=> Student(arr(0).toInt,arr(1).trim,arr(2).toInt))
    //5.将Rdd转换为DF
    val dataFrame: DataFrame = rdd.toDF()
    //B1.将dataFrame转换为rdd对象
    val rdd1:RDD[Row] = dataFrame.rdd
    //B2.通过collect()获取rdd1中的数据
    val array:Array[Row] = rdd1.collect()
    //B3.输出结果
    println(array(0))
    val name: Any = array(0)(1)
    println(name.toString)
    println(array(0).getAs[String]("name"))
    println(array(0).getString(1))
    spark.stop()
  }
}
