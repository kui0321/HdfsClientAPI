package org.example.spark.SSRWP

import org.apache.spark.SparkConf
import org.apache.spark.sql.{SaveMode, SparkSession}

object SSRWHive {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("SSRWHive")
    val spark = SparkSession.builder().config(conf).enableHiveSupport().getOrCreate()
    import spark.implicits._
    spark.sql("create database if not exists spark")
    spark.sql("use spark")
    spark.sql("drop table if exists student_infos")
    spark.sql("drop table if exists student_score")
    spark.sql(
      """
        |create table if not exists student_infos (
        |name string,age int)
        |row format delimited fields terminated by '\t'
        |""".stripMargin)
    spark.sql("drop table if exists student_score")
    spark.sql(
      """
        |create table if not exists student_score (
        |name string,
        |score int)
        |row format delimited fields terminated by '\t'
        |""".stripMargin)
    spark.sql("show tables").show
    spark.sql("load data inpath '/wordcount/input/student_infos' into table student_infos")
    spark.sql("load data inpath '/wordcount/input/student_score' into table student_score")
    spark.sql("select name,age from student_infos").show
    spark.sql("select name,age from student_score").show

    val df = spark.sql(
      """
        |select si.name,si.age,ss.score
        |from student_infos si,student_score ss
        |where si.name = ss.name
        |""".stripMargin)
    df.show(100)
    spark.sql("drop table if exists good_student_infos")
    df.write.mode(SaveMode.Overwrite).saveAsTable("good_student_infos")
    spark.close()
  }
}
