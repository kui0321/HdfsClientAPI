package org.example.spark.core

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object CoreBroadcast {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("CoreBroadcast")
    val sc = new SparkContext(conf)
    //1.定义一个本地的集合变量
    val localList = List("张三", "李四", "王五")
    //将本地集合变量定义为一个广播变量
    val broadcast: Broadcast[List[String]] = sc.broadcast(localList)
    //3.创建RDD对象
    val rddName: RDD[String] = sc.parallelize(List("张三", "关羽", "赵云", "李四", "张三", "刘备", "张飞", "王五"))
    //自定义一个函数
    def filterFunc(name: String) : Boolean = {
      //在使用本地集合变量的地方，从广播变量中取出
      val blackNames: List[String] =broadcast.value
      //判断当前的name是否在blackNames中出现
      if (blackNames.contains(name)) {
        false  //出现则过滤掉
      }else {
        true   //未出现则保留
      }
    }
    //4.使用广播变量
    val result: RDD[String] = rddName.filter(filterFunc)
    println(result.collect().mkString(","))
    sc.stop()
  }
}
