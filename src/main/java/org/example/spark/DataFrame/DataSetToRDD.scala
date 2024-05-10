package org.example.spark.DataFrame

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Dataset, SparkSession}

object DataSetToRDD {
  def main(args: Array[String]): Unit = {
    //创建spark运行环境
    val conf = new SparkConf().setMaster("local").setAppName("DataSetToRDD")
    //创建spark运行环境
    val spark = SparkSession.builder().config(conf).getOrCreate()
    //添加隐式转换
    import spark.implicits._
    //读取文件并使用样例类创建RDD对象
    val rdd:RDD[Student] = spark.sparkContext.textFile("file:///BIgdate/bigdateinfo/spark/软件/data/sql/student.txt")
      .map(_.split(","))
      .map(arr=>Student(arr(0).toInt,arr(1),arr(2).toInt))
    //将rdd转换为dataFrame
    val ds:Dataset[Student] = rdd.toDS()
    ds.printSchema()
    ds.show()
    //将dataFrame转换为rdd
    val student:Array[Student] = rdd.collect()
    println(student(0))
    println(student(0).id)
    println(student(0).name)
    println(student(0).age)
    //释放资源
    spark.stop()
  }
}
