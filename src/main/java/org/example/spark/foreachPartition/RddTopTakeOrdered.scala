package org.example.spark.foreachPartition

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RddTopTakeOrdered {
  def main(args: Array[String]): Unit = {
    //构建SparkConf对象，并设置本地运行和程序名称
    val conf = new SparkConf().setMaster("local[*]").setAppName("RddTopTakeOrdered")
    //使用conf对象构建SparkContet对象
    val sc = new SparkContext(conf)
    val rdd:RDD[Int] = sc.parallelize(List(5, 2, 1, 4, 3, 8, 6, 1))
    //获取默认降序的top3
    val top3: Array[Int] = rdd.top(3)
    println(top3.mkString(","))
    //获取默认升序的top3
    val takeOrdered:Array[Int] = rdd.takeOrdered(3)
    println(takeOrdered.mkString(","))
    //获取升序的top3
    val top3r: Array[Int] = rdd.top(5)(Ordering.Int.reverse)
    println(top3r.mkString(","))
    //获取降序的top3
    val takeOr3r:Array[Int] = rdd.takeOrdered(5)(Ordering.Int.reverse)
    println(takeOr3r.mkString(","))
    //关闭sc对象
    sc.stop()
  }
}
