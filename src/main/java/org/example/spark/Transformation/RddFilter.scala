package org.example.spark.Transformation

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RddFilter {
  def main(args: Array[String]): Unit = {
    //构建Saprkconf对象，并设置本地运行及作业名称
    val conf = new SparkConf().setMaster("local[*]").setAppName("filter")
    //构建SparkContext对象
    val sc = new SparkContext(conf)
    //创建RDD对象
    val rdd:RDD[Int] = sc.parallelize(List(1,2,3,4,5,8))
    //调用filter算子进行规则规则过滤
    val rddFilter1:RDD[Int] = rdd.filter(x=>x%2 == 0)
    //使用隐式写法
    val rddFilter2:RDD[Int] = rdd.filter(_ % 2 != 0)
    println(rddFilter1.collect().mkString(","))
    println(rddFilter2.collect().mkString(","))
  }
}
