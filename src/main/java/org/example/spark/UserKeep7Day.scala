package org.example.spark

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext.jarOfObject
import org.apache.spark.sql.{DataFrame, SparkSession}

object UserKeep7Day {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("UserKeep7Day")
    val spark = SparkSession.builder().config(conf).getOrCreate()
    jarOfObject(spark.sparkContext)
    val registInfos:DataFrame = spark.read.option("header","true").csv("file:///BIgdate\\bigdateinfo\\spark\\软件\\data\\demo\\registuser.csv")
    val logininfos:DataFrame = spark.read.option("header","true").csv("file:///BIgdate\\bigdateinfo\\spark\\软件\\data\\demo\\loginInfos.csv")
    //注册临时表
    registInfos.createTempView("regist")
    logininfos.createTempView("login_infos")
    //5.去重 distinct uid,login_date   将同一个用户的同一天登录的数据去重保留一条即可
    spark.sql(
      """
        |select distinct uid,login_date from login_infos
        |""".stripMargin).createTempView("login")
    spark.sql(
      """
        |select * from regist
        |""".stripMargin).show()

    spark.sql(
      """
        |select b.uid,b.regist_day,a.login_date,
        |datediff(from_unixtime(unix_timestamp(a.login_date,'yyyyMMdd'),'yyyy-MM-dd'),
        |from_unixtime(unix_timestamp(b.regist_day,'yyyyMMdd'),'yyyy-MM-dd')) as diff
        |from login a join regist b
        |on a.uid =b.uid
        |""".stripMargin).createTempView("temp")

    //统计注册日期 7日留存情况(不包含注册当天登录的)
    spark.sql(
      """
        |select regist_day,diff,count(uid) usercount from temp
        |where diff > 0
        |and diff <= 7
        |group by regist_day,diff
        |order by regist_day,diff
        |""".stripMargin).show()
    spark.close()
  }
}
