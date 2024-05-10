package org.example.spark.SSRWP

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

object SSRWText {
  def main(args: Array[String]): Unit = {
    ////1.创建配置文件对象
    val conf = new SparkConf().setMaster("local[*]").setAppName("SSRWText")
    //2.创建SparkSession对象
    val spark = SparkSession.builder().config(conf).getOrCreate()
    ////4.读取本地text文件，返回DataFrame对象
    val df:DataFrame = spark.read.text("file:///BIgdate/bigdateinfo/spark/软件/data/sql/student.txt")
    df.show()
    ////5.写文件
    //mode("append")参数的介绍
    //"error" (default)如果文件已经存在则抛出异 常 / data / sqlout / text already exists.
      //"append":如果存在则追加
      //"overwrite"：如果存在则覆盖
      //"ignore":如果文件存在则忽略
    df.write.mode("append").format("error").save("file:///BIgdate/bigdateinfo/spark/软件/data/sqlout/student.txt")
    spark.stop()
  }
}
