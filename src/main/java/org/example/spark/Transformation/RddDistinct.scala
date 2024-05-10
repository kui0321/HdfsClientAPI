package org.example.spark.Transformation

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RddDistinct {
  def main(args: Array[String]): Unit = {
    //构造SparkConf对象，设置本地运行及设置运行名称
    val conf = new SparkConf().setMaster("local[*]").setAppName("distinct")
    //构造SaprkContext
    val sc = new SparkContext(conf)
    //创建RDD对象
    val rdd:RDD[Int] = sc.parallelize(List(1,2,3,4,2,5,4,7))
    //使用distinct对集合进行去重
    println(rdd.distinct().collect().mkString(","))
    val rdd1:RDD[(Char, Int)] = sc.parallelize(List(('x', 1), ('x', 2),
      ('x', 1), ('y', 1)))
    println(rdd1.distinct().collect().mkString(","))
    sc.stop()
  }
}
