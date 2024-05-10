package org.example.spark.DataFrame

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}

object ToDFDemo1 {
  def main(args: Array[String]): Unit = {
    //1.创建配置文件对象
    val conf:SparkConf = new SparkConf().setMaster("local[*]").setAppName("ToDFDemo1")
    ////2.创建SparkSession对象
    val spark:SparkSession = SparkSession.builder().config(conf).getOrCreate()
    ////4.添加隐式转换
    import spark.implicits._
    //5.读取本地文件，并映射创建RDD
    val rdd:RDD[(Int,String,Int)] = spark.sparkContext.textFile("file:///BIgdate/bigdateinfo/spark/软件/data/sql/student.txt")
      .map(_.split(","))
      .map(arr =>(arr(0).toInt,arr(1).trim,arr(2).toInt))
    //6.通过rdd.toDF(colNames: String*)
    val df:DataFrame = rdd.toDF("id","name","age")
    //7.输出结构信息
    df.printSchema()
    /** show(numRows: Int, truncate: Boolean)
     * numRows:表示输出数据的行数,默认是20行.
     * truncate:表示输出时是否对列的值进行截取
     * false:表示不截取
     * true:表示截取，保留20个字符
     */
    df.show(2,false)
    spark.stop()
  }
}
