package org.example.spark.foreachPartition

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RddReduce {
  def main(args: Array[String]): Unit = {
    //2.构建SparkConf对象，并设置本地运行和程序名称
    val conf = new SparkConf().setMaster("local[*]").setAppName("RddReduce")
    //3.使用conf对象构建SparkContet对象
    val sc = new SparkContext(conf)
    //5.创建RDD对象
    val rdd:RDD[Int] = sc.parallelize(List(1, 2, 3, 4))
    //6.将rdd中所有元素做聚合加操作
    val sum: Int = rdd.reduce(_ + _)
    println(sum)
    //4.关闭sc对象
    sc.stop()
  }
}
