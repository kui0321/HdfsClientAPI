package org.example.spark.Transformation

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RddGlom {
  def main(args: Array[String]): Unit = {
    //2.构建SparkConf对象，并设置本地运行和程序名称
    val conf = new SparkConf().setMaster("local[*]").setAppName("glom")
    //3.使用conf对象构建SparkContet对象
    val sc = new SparkContext(conf)
    //5.创建Rdd
    val rdd:RDD[Int] = sc.parallelize(Array(1,2,3,4,5,6,7,8),2)
    //6.rdd1.glom()将rdd的数据加上一个嵌套，这个嵌套是按照分区进行的
    val rdd2:RDD[Array[Int]] = rdd.glom()
    val rddc1:Array[Int] = rdd.collect()
    val rddc2:Array[Array[Int]] = rdd2.collect()
    println(rddc1.mkString(","))
    rddc2.foreach(arr=>println(arr.mkString(",")))
    //4.关闭sc对象
    sc.stop()
  }
}
