package org.example.spark.Transformation

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object TransformationFlatMap {
  def main(args: Array[String]): Unit = {
    //构建SparkConf对象，并设置本地运行和程序名称
    val conf = new SparkConf().setMaster("local[*]").setAppName("flagMap")
    //构建SparkContext对象
    val sc = new SparkContext(conf)
    //创建RDD对象
    val rdd:RDD[String] = sc.parallelize(List("a a b","a b c","b c d"))
    //将集合中的每一各元素按空格拆分，生成新的RDD
    val rdd2:RDD[String] = rdd.flatMap(_.split(" "))
    println(rdd2.collect().mkString(","))
    sc.stop()
  }
}
