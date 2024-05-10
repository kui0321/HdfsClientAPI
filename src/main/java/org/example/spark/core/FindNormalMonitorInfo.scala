package org.example.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object FindNormalMonitorInfo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("FindNormalMonitorInfo")
    val sc = new SparkContext(conf)
    val baseInfos:RDD[String] = sc.textFile("file:///BIgdate/bigdateinfo/spark/软件/data/core/monitor_camera_info")
    val monitorInfos:RDD[String] = sc.textFile("file:///BIgdate/bigdateinfo/spark/软件/data/core/monitor_flow_action")
    val baseMonitorCount:RDD[(String,Int)] = baseInfos.map(line => {
      (line.split("\t")(0),1)
    }).reduceByKey(_ + _)
    val monitorCount: RDD[(String,Int)] = monitorInfos.map(line=>{
      val array: Array[String] = line.split("\t")
      array(1) +"_"+ array(2)
    }).distinct().map(ele=>{
      (ele.split("_")(0),1)
    }).reduceByKey(_ + _)
    val joinInfos:RDD[(String, (Int, Int))] = baseMonitorCount.join(monitorCount)
    val normalMonitorInfos:RDD[(String, (Int, Int))] = joinInfos.filter(tp=>{
      tp._2._1 == tp._2._2
    })
    normalMonitorInfos.foreach(tp=>{
      println(s"正常的卡口信息：${tp._1}")
    })
    sc.stop()
  }
}
