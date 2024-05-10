package org.example.spark.SSRWP

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

object SSRWParquet {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("SSRWParquet")
    val spark = SparkSession.builder().config(conf).getOrCreate()
    //4.读取本地parquet文件，返回DataFrame对象
    //format("parquet/csv/json/text/jdbc") 指定读取文件的格式
    val df:DataFrame = spark.read.format("parquet").load("file:///BIgdate/bigdateinfo/spark/软件/data/sql/student.parquet")
      .select("id","name","age")
    df.printSchema()
    df.show()
    //由于默认的读取的文件格式为parquet，所以还可以省略format("parquet")
    //如果spark.sql.sources.default被修改过，不能省略format("parquet")
    val df1:DataFrame = spark.read.load("file:///BIgdate/bigdateinfo/spark/软件/data/sql/student.parquet")
    val df2:DataFrame = spark.read.parquet("file:///BIgdate/bigdateinfo/spark/软件/data/sql/student.parquet")
    ////5.写文件
    //由于默认的读取的文件格式为parquet，所以还可以省略format("parquet")
    //如果spark.sql.sources.default被修改过，不能省略format("parquet")
    df2.write.save("file:///BIgdate/bigdateinfo/spark/软件/data/sqlout/student.parquet")
    spark.stop()
  }
}
