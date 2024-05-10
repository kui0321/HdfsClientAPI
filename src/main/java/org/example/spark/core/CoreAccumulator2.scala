package org.example.spark.core

import org.apache.spark.util.LongAccumulator
import org.apache.spark.{SparkConf, SparkContext}

object CoreAccumulator2 {
  def main(args: Array[String]): Unit = {
    // 创建SparkConf并设置App名称
    val conf = new SparkConf().setMaster("local[*]").setAppName("CoreAccumulator2")
    // 创建SparkContext，该对象是提交Spark App的入口
    val sc = new SparkContext(conf)
    //创建RDD对象
    val rdd = sc.parallelize(Range(1,26,1).toList,2)
    // 创建累加器
    val sum:LongAccumulator = sc.longAccumulator("sum")
    // 执行map操作
    def mapFunc(data:Int)={
      sum.add(1)
      print(sum.value+",")
      data
    }
    // 执行map操作
    val result:Array[Int] = rdd.map(mapFunc).collect()
    println("----------------------------------------")
    println(result.mkString(","))
    println("driver sum:"+sum.value)
    sc.stop()
  }
}
