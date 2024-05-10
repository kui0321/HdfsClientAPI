package org.example.spark.foreachPartition

import org.apache.spark.{SparkConf, SparkContext}

object RddFirstTakeCount {
  def main(args: Array[String]): Unit = {
    //构建SparkConf对象，并设置本地运行和程序名称
    val conf = new SparkConf().setMaster("local[*]").setAppName("RddFirstTakeCount")
    //使用conf对象构建SparkContet对象
    val sc = new SparkContext(conf)
    //创建RDD对象
    val rdd = sc.parallelize(List(5, 2, 1, 4, 3, 8, 6, 1))
    //获取第一个元素
    val first:Int = rdd.first()
    println(first)
    //获取rdd前三个元素
    val threeEle:Array[Int] = rdd.take(3)
    println(threeEle.mkString(","))
    //获取rdd中元素的总数
    val eleNam: Long = rdd.count()
    println(eleNam)
    //关闭sc对象
    sc.stop()
  }
}
