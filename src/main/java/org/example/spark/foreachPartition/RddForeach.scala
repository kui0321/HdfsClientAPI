package org.example.spark.foreachPartition

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RddForeach {
  def main(args: Array[String]): Unit = {
    //构建SparkConf对象，并设置本地运行和程序名称
    val conf:SparkConf = new SparkConf().setMaster("local[*]").setAppName("foreach")
    //使用conf对象构建SparkContet对象
    val sc = new SparkContext(conf)
    //创建RDD对象
    val rdd:RDD[String] = sc.parallelize(List("a", "b", "c", "e", "f",
      "d"), 3)
    //自定义函数
    def process(ele:String):Unit = {
      println("开启process..")
      println("当前数据" + ele)
      println("结束process..")
    }
    //调用foreach算子,参数为自定义函数
    rdd.foreach(process)
    println("------------------")
    //可以为foreach算子指定print\println函数
    rdd.foreach(println)
    //关闭sc对象
    sc.stop()
  }
}
