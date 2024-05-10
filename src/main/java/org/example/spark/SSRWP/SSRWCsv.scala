package org.example.spark.SSRWP

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

object SSRWCsv {
  def main(args: Array[String]): Unit = {
    //1.创建配置文件对象
    val conf = new SparkConf().setMaster("local[*]").setAppName("SSRWCsv")
    //2.创建SparkSession对象
    val spark = SparkSession.builder().config(conf).getOrCreate()
    //4.读取本地csv文件，返回DataFrame对象
    val df:DataFrame = spark.read.format("csv")
      .option("header","true") //读取csv文件第一行作为表头
      .option("delimiter",",") //读取csv文件分隔符
      .option("inferSchema","true") //读取csv文件字段类型
      .option("encoding","UTF-8")  //读取csv文件编码格式
      .load("file:///BIgdate/bigdateinfo/spark/软件/data/sql/student.csv") // 读取本地csv文件
    df.printSchema() //输出表结构
    df.show() //输出表数据
    df.write.mode(SaveMode.Overwrite)  //写入csv文件
      .option("header","true")
      .option("delimiter",",")
      .option("encoding","UTF-8")
      .csv("file:///BIgdate/bigdateinfo/spark/软件/data/sqlout/student.csv")
    spark.stop()  //释放资源
  }
}
