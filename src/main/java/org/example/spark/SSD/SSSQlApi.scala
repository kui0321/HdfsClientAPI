package org.example.spark.SSD

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

object SSSQlApi {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("SSSQlApi")
    val spark:SparkSession = SparkSession.builder().config(conf).getOrCreate()
    val df:DataFrame = spark.read.format("csv")
      .option("header","true")
      .option("delimiter",",")
      .option("inferSchema","true")
      .option("encoding","UTF-8")
      .load("file:///BIgdate/bigdateinfo/spark/软件/data/sql/score.csv")
    //将df注册为一个临时视图(表),只能在当前的SparkSession对象中使用
    df.createTempView("tb_score")
    ////注册或替换临时视图 不存在则注册，存在则替换
    df.createGlobalTempView("tb_score")
    //注册全局的临时视图
    df.createOrReplaceGlobalTempView("tb_score3")

    spark.sql(
      """
        |select * from tb_score
        |""".stripMargin).show()
    spark.sql(
      """
        |select name,count(1) as cut
        |from tb_score group by name
        |""".stripMargin).show()
    //查询操作，全局的视图。需要在表名前带上global_temp.
    spark.sql(
      """
        |select project,count(1) as cnt
        |from global_temp.tb_score3
        |group by project
        |""".stripMargin).show()
    spark.close()
  }
}
