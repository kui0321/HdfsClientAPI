package org.example.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object FindTop5MonitorInfo {
  def main(args: Array[String]): Unit = {
    //1.构建SparkConf对象
    val conf = new SparkConf().setMaster("local[*]").setAppName("FindTop5MonitorInfo")
    //2.使用conf对象构建SparkContext对象
    val sc = new SparkContext(conf)
    //3.读取数据
    val monitorInfos:RDD[String] = sc.textFile("file:///BIgdate/bigdateinfo/spark/软件/data/core/monitor_flow_action")
    //4.将数据整理成(monitorId,1)的格式
    val top5MonitorInfos:Array[String] = monitorInfos.map(line=>{
      (line.split("\t")(1),1)
      //5.将整理好的数据进行聚合
    }).reduceByKey(_+_).sortBy(tp=>{tp._2},false).map(tp=>{tp._1})
      .take(5)
    top5MonitorInfos.foreach(println)
    //6.将整理好的数据进行排序
    monitorInfos.filter(line=>{
      top5MonitorInfos.contains(line.split("\t")(1))
    }).foreach(println)
    sc.stop()
  }
}
