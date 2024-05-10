package org.example.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object FindCarInfo {
  def main(args: Array[String]): Unit = {
    //1.构建SparkConf对象
    val conf = new SparkConf().setMaster("local[*]").setAppName("FindCarInfo")
    //2.使用conf对象构建SparkContext对象
    val sc = new SparkContext(conf)
    //3.读取数据
    val monitorInfos:RDD[String] = sc.textFile("file:///BIgdate/bigdateinfo/spark/软件/data/core/monitor_flow_action")
    //4.将数据整理成(carId,1)的格式
    val areaOneCars:RDD[String] = monitorInfos.filter(line=>{
      "01".equals(line.split("\t")(7))
    }).map(line=>{
      line.split("\t")(3)
    }).distinct()
    //5.找出01区域下通过的所有车牌
    val areaFiveCars:RDD[String] = monitorInfos.filter(line=>{
      "05".equals(line.split("\t")(7))
    }).map(line=>{
      line.split("\t")(3)
    }).distinct()
    //6.找出05区域下通过的所有车牌
    val result:RDD[String] = areaOneCars.intersection(areaFiveCars)
    result.foreach(car=>{
      println(s"区域01与区域05都出现的车辆的车牌：$car")
    })
    sc.stop()
  }
}
