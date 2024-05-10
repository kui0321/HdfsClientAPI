package org.example.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object CreateByParallelize {
  def main(args: Array[String]): Unit = {
    //2.构建SparkConf对象。并设置本地运行和程序的名称
    val conf = new SparkConf().setMaster("local[2]").setAppName("CreateRdd1")
    //3.构建SparkContext对象
    val sc = new SparkContext(conf)
    // 4.通过并行化创建RDD对象：将本地集合->分布式的RDD对象
    val rdd: RDD[Int] = sc.parallelize(List(1,2,3,4,5,6),3)
    println(rdd.getNumPartitions)
    val array1:Array[Int] = rdd.collect()
    println(array1.mkString(","))
    val array2:Array[Array[Int]] = rdd.glom().collect()
    array2.foreach(err=>println(err.mkString(",")))
  }
}
