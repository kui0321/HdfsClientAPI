package org.example.spark.Transformation

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RddReduceByKey {
  def main(args: Array[String]): Unit = {
    //构建Sparkconf对象，并设置本地运行及作业名称
    val conf = new SparkConf().setMaster("local[*]").setAppName("reduceByKey")
    //构建SparkContext对象
    val sc = new SparkContext(conf)
    //定义RDD对象
    val rdd:RDD[(String,Int)] = sc.parallelize(List(("a", 1), ("a", 2),
      ("b", 1), ("a", 3), ("b", 1), ("a", 4)))
    //将相同的key的value值做聚合计算
    val rdd1:RDD[(String, Int)] = rdd.reduceByKey((x, y)=>x + y)
    //隐式函数的写法
    val rdd2:RDD[(String, Int)] = rdd.reduceByKey(_+_)
    println(rdd1.collect().mkString(","))
    println(rdd2.collect().mkString(","))
  }
}
