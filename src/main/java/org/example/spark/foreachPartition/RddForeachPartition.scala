package org.example.spark.foreachPartition

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

object RddForeachPartition {
  def main(args: Array[String]): Unit = {
    //构建SparkConf对象，并设置本地运行和程序名称
    val conf:SparkConf = new SparkConf().setMaster("local[*]").setAppName("ForeachPartition")
    //使用conf对象构建SparkContet对象
    val sc = new SparkContext(conf)
    //创建RDD对象
    val rdd: RDD[String] = sc.parallelize(List("a", "b", "c", "e", "f", "d"), 3)
    //自定义处理函数
    def process(datas:Iterator[String]):Unit={
      println("操作一-开始一个分区...")
      val result = ListBuffer[String]()
      for (data<-datas){
        result.append(data)
      }
      println("当前分区的数据:" + result)
      println("操作二-结束一个分区...")
    }
    //调用foreachPartition算子，参数为自定义函数
    rdd.foreachPartition(process)
    //关闭sc对象
    sc.stop()
  }
}
