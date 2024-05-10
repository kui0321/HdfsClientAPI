package org.example.spark.Transformation

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RddSample {
  def main(args: Array[String]): Unit = {
    //构建SparkConf对象，并设置本地运行和程序名称
    val conf = new SparkConf().setMaster("local[*]").setAppName("sample")
    //使用conf对象构建SparkContet对象
    val sc = new SparkContext(conf)
    //创建RDD对象
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7, 9, 0), 1)
    val rdd2:RDD[Int] = rdd.sample(true, 2)
    println("2 " + rdd2.collect().mkString(","))
    //当种子相同（前两个参数的值也相同）时，多次抽取的结果相同
    val rdd3:RDD[Int] = rdd.sample(false,0.3,6)
    val rdd4:RDD[Int] = rdd.sample(true,2,3)
    println(rdd3.collect().mkString(","))
    println(rdd4.collect().mkString(","))
    //关闭sc对象
    sc.stop()
  }
}
