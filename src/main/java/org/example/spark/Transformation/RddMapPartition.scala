package org.example.spark.Transformation

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

object RddMapPartition {
  def main(args: Array[String]): Unit = {
    //构建SparkConf对象，并设置本地运行和程序名称
    val conf = new SparkConf().setMaster("local[*]").setAppName("mapPartition")
    //使用conf对象构建SparkContet对象
    val sc = new SparkContext(conf)
    //创建RDD对象
    val rdd:RDD[String] = sc.parallelize(List("a", "b", "c", "d", "e", "f"), 3)
    //自定义一个处理函数
    def process(datas:Iterator[String]):Iterator[String] = {
      println("创建数据库连接...")
      val result = ListBuffer[String]()
      for (ele <- datas){
        result.append(ele)
      }
      println("批量插入数据："+result)
      println("关闭数据库连接...")
      result.iterator
    }
    //调用mapPartitions操作
    val resultRdd: RDD[String] = rdd.mapPartitions(process)
    resultRdd.count()
    //关闭sc对象
    sc.stop()
  }
}
