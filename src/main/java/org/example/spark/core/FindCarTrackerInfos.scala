package org.example.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import java.text.SimpleDateFormat

object FindCarTrackerInfos {
  def main(args: Array[String]): Unit = {
    //创建SparkConf并设置App名称
    val conf = new SparkConf().setMaster("local[*]").setAppName("FindCarTrackerInfos")
    //创建SparkContext，该对象是提交Spark App的入口
    val sc = new SparkContext(conf)
    //读取数据
    val monitorInfos:RDD[String] = sc.textFile("file:///BIgdate/bigdateinfo/spark/软件/data/core/monitor_flow_action")
    //找出卡扣0001下通过的所有车辆信息
    val allCars:Array[String] = monitorInfos.filter(line=>{
      "0001".equals(line.split("\t")(1))
    }).map(line=>{line.split("\t")(3)})
      .distinct().collect()
    ////分析车辆轨迹
    monitorInfos.filter(line=>{
      //获取0001卡口下通过车辆的所有数据
      val car = line.split("\t")(3)
      allCars.contains(car)
    }).map(line=>{
      ////将日志数据转为二元组，便于后续分组
      val car = line.split("\t")(3)
      (car,line)
    }).groupByKey().map(tp=>{//将同一个车牌数据分为一组
      val car = tp._1
      val list:List[String] = tp._2.toList
      // //将同一车按照时间升序排序
      val sortedList:List[String] = list.sortWith((s1,s2)=>{
        //获取时间字符串
        val actionTime1 = s1.split("\t")(4)
        val actionTime2 = s2.split("\t")(4)
        ////转换为日期类型
        val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val time1 = sdf.parse(actionTime1)
        val time2 = sdf.parse(actionTime2)
        //比较大小，顺序不对的话调换一下顺序
        time1.before(time2)
      })
      //拼接轨迹字符串 卡口1_时间1->卡口2_时间
      var carTracker = ""
      for (line <- sortedList){
        //封装为二元组(车牌号,轨迹字符串并去掉最后面的两个字符->)
        carTracker += line.split("\t")(1)+"_"+line.split("\t")(4)+"->"
      }
      //封装为二元组(车牌号,轨迹字符串并去掉最后面的两个字符->)
      (car,carTracker.substring(0,carTracker.length-2))
    }).foreach(tp=>{
      println(s"${tp._1}的轨迹信息：${tp._2}")
    })
  }
}
