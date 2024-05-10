package org.example.spark.foreachPartition

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RddCountByKey {
  def main(args: Array[String]): Unit = {
    //2.构建SparkConf对象，并设置本地运行和程序名称
    val conf=new SparkConf().setMaster("local[*]").setAppName("RddCountByKey")
    //3.使用conf对象构建SparkContet对象
    val sc = new SparkContext(conf)
    //5.创建KeyValue型RDD对象
    val rdd = sc.parallelize(List(("a", 1), ("b", 1), ("a", 2), ("b", 2), ("a", 3), ("a", 4)))
    //6.复习转换算子reduceByKey：将相同key的value值做聚合操作
    val rdd2:RDD[(String, Int)] = rdd.reduceByKey(_ + _)
    println(rdd2.collect().mkString(","))
    //7.行动算子countByKey 表示统计key出现次数
    val result: collection.Map[String, Long] = rdd.countByKey()
    //打印计算结果。
    result.foreach(println)
    //关闭SparkContext对象。
    sc.stop()
  }
}
