package org.example.spark.UDF

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

case class AccInfo(uid:String,accDate:String,dur:Int)

object UserDurInfo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("UserDurInfo")
    val spark = SparkSession.builder().config(conf).getOrCreate()

    spark.sparkContext.setLogLevel("Error")
    val infos = spark.sparkContext.textFile("file:///BIgdate\\bigdateinfo\\spark\\软件\\data\\demo\\userAccInfo.txt")
    val accInfoRDD = infos.map(info=>{
      val arr = info.split("\t")
      val uid = arr(0)
      val accDate = arr(1)
      val dur = arr(2).toInt
      AccInfo(uid,accDate,dur)
    })
    import spark.implicits._
    val frame = accInfoRDD.toDF()
    frame.createTempView("accInfo")
    //统计每个用户每天访问网站的总时长（当天总时长是累加之前日期的）
    spark.sql(
      """
        |select uid,accDate,dur,sum(dur)over(partition by uid order by accDate) AS current_day_dur
        | from accInfo
        |""".stripMargin).show()
    spark.close()
  }
}
