package org.example.spark.Transformation

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object TransformationMap {
  def main(args: Array[String]): Unit = {
    val conf:SparkConf = new SparkConf().setMaster("local[*]").setAppName("map")
    val sc = new SparkContext(conf)
    val rdd:RDD[Int] = sc.parallelize(List(1,2,3,4,5,6))
    def funcA(x:Int):Int={
      if (x%2 != 0){
        x*2
      }else{
        x
      }
    }
    val rdd1:RDD[Int] = rdd.map(funcA)
    println(rdd1.collect().mkString(","))
    //匿名函数的用法
    def funcB(x:Int):Int= {
      x*2
    }
    val rdd3:RDD[Int] = rdd.map(funcB)
    println(rdd3.collect().mkString(","))
    println(rdd.map(x=>x*2).collect().mkString(","))
    println(rdd.map(_*2).collect().mkString(","))
    sc.stop()
  }
}
