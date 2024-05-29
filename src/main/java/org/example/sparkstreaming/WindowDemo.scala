package org.example.sparkstreaming

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Durations, Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object WindowDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("WindowDemo")
    val sc = new SparkContext(conf)
    //设置日志级别
    sc.setLogLevel("Error")
    //创建StreamingContext对象
    val ssc = new StreamingContext(sc,Seconds(5))
    val lines = ssc.socketTextStream("node1",9999)
    //黑名单列表
    val blackList = List[String]("hadoop","spark","zhangsan","lisi","wangwu")
    //将本地集合变量定义为一个广播变量
    val broadcast:Broadcast[List[String]] = sc.broadcast(blackList)
    //窗口操作
    val windowDs:DStream[String] = lines.window(Durations.seconds(15),Durations.seconds(30))
    val result: DStream[String] = windowDs.filter(line=>{
      val blackNmaeList:List[String] = broadcast.value
      !blackNmaeList.contains(line.split(" ")(1))
    })
    //打印
    result.print()
    //启动
    ssc.start()
    //等待执行停止
    ssc.awaitTermination()
  }
}
