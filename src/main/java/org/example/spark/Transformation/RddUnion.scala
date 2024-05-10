package org.example.spark.Transformation

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RddUnion {
  def main(args: Array[String]): Unit = {
    //构建SparkConf 对象，并设置本地运行和程序名称
    val conf = new SparkConf().setMaster("local[*]").setAppName("union")
    //构建SparkContext对象
    val sc = new SparkContext(conf)
    //创建RDD对象
    val rdd1:RDD[Int] = sc.parallelize(Array(1,2,3,4))
    val rdd2:RDD[Int] = sc.parallelize(Array(5,6,7,8))
    val rdd3:RDD[String] = sc.parallelize(Array("x","y"))
    //union算子将两个rdd对象元素做并集计算，并生成一个新的rdd对象
    val result1:Array[Int] = rdd1.union(rdd2).collect()
    //union算子不会去重，无方向性
    println(result1.mkString(","))
    val result2:Array[Int] = rdd2.union(rdd1).collect()
    println(result2.mkString(","))
    //使用union做并集计算的两个rdd的数据类型要一致，否则出错
  }
}
