package org.example.spark.UDF

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

object OpenWindowFunction {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("UDF")
    val spark = SparkSession.builder().config(conf).getOrCreate()
    //3.读取json文件
    val df:DataFrame = spark.read.json("file:///BIgdate\\bigdateinfo\\spark\\软件\\data\\sql\\score.json")
    //4.注册视图
    df.createOrReplaceTempView("tb_score")
    //5.1.在每行信息后面显示全部成绩的平均分
    println("avg(score) over() as avg_score")
    spark.sql(
      """
        |select  id,name,project,score,avg(score) over() as avg_score
        |from tb_score
        |""".stripMargin).show()
    spark.close()
    //5.2.在每行信息后面显示当前科目的平均分
    println("avg(score) over(partition by project) as avg_score")
    spark.sql(
      """
        |select  id,name,project,score,
        |avg(score) over(partition by project) as avg_score
        |from tb_score
        |""".stripMargin).show()
    spark.close()
  }
}
