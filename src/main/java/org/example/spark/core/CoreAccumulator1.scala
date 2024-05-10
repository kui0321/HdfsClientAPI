package org.example.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object CoreAccumulator1 {
  def main(args: Array[String]): Unit = {
    // 创建SparkConf并设置App名称
    val conf = new SparkConf().setMaster("local[*]").setAppName("CoreAccumulator1")
    // 创建SparkContext，该对象是提交Spark App的入口
    val sc = new SparkContext(conf)
    //创建RDD对象
    val rdd:RDD[Int] = sc.parallelize(Range(1,26,1).toList,2)
    var sum:Long = 0
    // 创建累加器
    def mapFunc(data:Int):Int={
      sum = sum + 1
      print(sum+",")
      data
    }
    // 执行map操作
    val result:Array[Int] = rdd.map(mapFunc).collect()
    println("----------------------------------------")
    println(result.mkString(","))
    println("driver sum:"+sum)
    sc.stop()
  }
}
