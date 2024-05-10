package org.example.spark.SSD

import org.apache.spark.SparkConf
import org.apache.spark.sql.{Column, DataFrame, SparkSession}
import org.apache.spark.sql.functions._

object SSFuntions {
  def main(args: Array[String]): Unit = {
    ///1.创建配置文件对象
    val conf = new SparkConf().setMaster("local[*]").setAppName("SSFuntions")
    ////2.创建SparkSession对象
    val spark = SparkSession.builder().config(conf).getOrCreate()
    import spark.implicits._
    //4.读取本地文件
    val df1:DataFrame = spark.read.text("file:///BIgdate\\bigdateinfo\\spark\\软件\\data/words.txt")
    val colSplit:Column = split(df1.col("value")," ")
    val explodeColumn:Column = explode(colSplit)
    //7.对已经存在的列进行操作，返回一个新的列
    val df2:DataFrame = df1.withColumn("value",explodeColumn)
    //8.分组统计单词出现的次数，并降序排列
    df2.groupBy("value")
      .count()  //为列名重命名
      .withColumnRenamed("value","word")
      .withColumnRenamed("count","num")
      .sort($"cnt".desc)  //排序，按照单词出现的数量的倒叙排序
      .show()
    //3.关闭spark
    spark.stop()
  }
}
