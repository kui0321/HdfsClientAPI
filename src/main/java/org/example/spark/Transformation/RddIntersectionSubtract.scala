package org.example.spark.Transformation

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RddIntersectionSubtract {
  def main(args: Array[String]): Unit = {
    //构建SparkConf对象，并设置本地运行和程序名称
    val conf = new SparkConf().setMaster("local[1]").setAppName("交集和差集")
    //使用conf对象构建SparkContet对象
    val sc = new SparkContext(conf)
    //创建5个RDD对象
    val rdd1:RDD[Int] = sc.parallelize(Array(1,2,3,4,9))
    val rdd2:RDD[Int] = sc.parallelize(Array(5,6,7,8,9))
    val rdd3: RDD[(Char, Int)] = sc.parallelize(List(('x', 6), ('y', 1)))
    val rdd4: RDD[(Char, Int)] = sc.parallelize(List(('x', 6), ('z', 3)))
    val rdd5: RDD[(Char, Int)] = sc.parallelize(List(('x', 5), ('z', 3)))
    //交集运算，将两个rdd中的相同元素获取后组成一个新的rdd，无方向性
    println(rdd1.intersection(rdd2).collect().mkString(","))
    //计算合集
    val rdd34:RDD[(Char, Int)] = rdd3.intersection(rdd4)
    val rdd43:RDD[(Char,Int)] = rdd4.intersection(rdd3)
    println(rdd34.collect().mkString(","))
    println(rdd43.collect().mkString(","))
    println(rdd4.intersection(rdd5).collect().mkString(","))
    //计算集合的差集
    println(rdd1.subtract(rdd2).collect().mkString(","))
  }
}
