package org.example.spark.SSRWP

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

object SSRWJson {
  def main(args: Array[String]): Unit = {
    //1.创建配置文件对象
    val conf = new SparkConf().setMaster("local[*]").setAppName("SSRWJson")
    //2.创建SparkSession对象
    val spark = SparkSession.builder().config(conf).getOrCreate()
    //4.读取本地json文件，返回DataFrame对象
    val df:DataFrame = spark.read.option("encoding","UTF-8")
      .json("file:///BIgdate/bigdateinfo/spark/软件/data/sql/student.json")
    df.show()
    //6.创建临时视图
    df.createTempView("student")
    //7.使用临时视图进行查询
    val dataFrame: DataFrame = spark.sql("""select * from student where age>20""".stripMargin)
    dataFrame.show()
    //8.写json文件
    df.write.mode("overwrite").format("json").save("file:///BIgdate/bigdateinfo/spark/软件/data/sqlout/student.json")
    //df.write.mode("overwrite").save("file:///BIgdate/bigdateinfo/spark/软件/data/sqlout/student.json")
    spark.stop()
  }
}
