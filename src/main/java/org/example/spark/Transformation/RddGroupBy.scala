package org.example.spark.Transformation

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RddGroupBy {
  def main(args: Array[String]): Unit = {
    //构建SparkConf对象，并设置本地运行和程序名称
    val conf = new SparkConf().setMaster("local[*]").setAppName("groupBy")
    //使用conf对象构建SparkContet对象
    val sc = new SparkContext(conf)
    //创建Rdd
    val rdd:RDD[(Char, Int)] = sc.parallelize(Array(('a', 1), ('a', 2),
      ('b', 1), ('b', 2), ('a', 3), ('a', 4)))
    //通过groupBy算子对rdd对象中的数据进行分组groupBy插入的函数的用意是指定按照谁进行分组
    //分组后的结果是有二元组组成的RDD
    val gbRdd:RDD[(Char,Iterable[(Char,Int)])] = rdd.groupBy(tupEle=>tupEle._1)
    ///收集到Driver端
    val result:Array[(Char,Iterable[(Char, Int)])] =  gbRdd.collect()
    println(result.mkString(","))
    //使用map转换算子
    val result1:Array[(Char,List[(Char,Int)])] = gbRdd.map(tup=>(tup._1,tup._2.toList)).collect()
    println(result1.mkString(","))
    //关闭sc对象
    sc.stop()
  }
}
