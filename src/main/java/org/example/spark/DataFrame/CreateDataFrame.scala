package org.example.spark.DataFrame

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

object CreateDataFrame {
  def main(args: Array[String]): Unit = {
    //1.创建上下文配置文件对象
    val conf = new SparkConf().setMaster("local[*]").setAppName("CreateDataFrame")
    //2.创建执行环境入口SparkSession对象
    val sparkSession: SparkSession = SparkSession.builder().config(conf).getOrCreate()
    //3.读取文件，映射创建RDD[Row]对象
    val rdd:RDD[Row] = sparkSession.sparkContext.textFile("file:///BIgdate/bigdateinfo/spark/软件/data/sql/student.txt")
      .map(_.split(","))
      .map(array => Row(array(0).toInt,array(1).trim, array(2).toInt))
    val schema:StructType = StructType(
      StructField("id",IntegerType,false)
      ::StructField("name",StringType,false)
      ::StructField("age", IntegerType,true)
      ::Nil)
    //5.基于rdd对象转为DataFrame
    val df:DataFrame = sparkSession.createDataFrame(rdd,schema)
    //6.打印df的表结构信息
    df.printSchema()
    //7.输出df中的数据
    df.show()
    //8.关闭资源
    sparkSession.stop()
  }
}
