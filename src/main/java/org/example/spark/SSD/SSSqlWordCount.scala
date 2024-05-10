package org.example.spark.SSD

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._

object SSSqlWordCount {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("SSSqlWordCount")
    val spark = SparkSession.builder().config(conf).getOrCreate()
    val rdd: RDD[String] = spark.sparkContext.textFile("file:///BIgdate/bigdateinfo/spark/软件/data/words.txt")
      .flatMap(_.split(" "))
    import spark.implicits._
    val df: DataFrame = rdd.toDF("word")
    df.createTempView("to_words")
    spark.sql(
      """
        |select word,count(1) as cut
        |from to_words
        |group by word
        |""".stripMargin).show()
    spark.sql(
      """
        |select *
        |from to_words
        |""".stripMargin).show()
    spark.close()
  }
}
