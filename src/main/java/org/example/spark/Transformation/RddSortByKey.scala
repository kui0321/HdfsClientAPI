package org.example.spark.Transformation

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RddSortByKey {
  def main(args: Array[String]): Unit = {
    //构建SparkConf对象，并设置本地运行和程序名称
    val conf = new SparkConf().setMaster("local[*]").setAppName("sortByKey")
    //构建SparkContext对象
    val sc = new SparkContext(conf)
    //创建Key-value型RDD
    val rdd:RDD[(Char,Int)] = sc.parallelize(List(('w', 2), ('h', 5),
      ('k', 9), ('m', 3), ('a', 7), ('p', 4), ('q', 1), ('n', 8), ('y', 6)))
    println(rdd.getNumPartitions)
    //默认按照key的升序排列，生成的RDD对象分区数与原RDD相同。
    val rdd1:RDD[(Char, Int)] = rdd.sortByKey()
    println(rdd1.collect().mkString(","))
    //按照key的降序排列，默认生成的RDD对象分区数与原RDD相同
    val rdd2:RDD[(Char, Int)] = rdd.sortByKey(ascending = false)
    println(rdd2.getNumPartitions)
    val result2:Array[(Char, Int)] = rdd2.collect()
    println(result2.mkString(","))
    //按照key的降序排列，设定生成的RDD对象分区数为4
    val rdd3:RDD[(Char, Int)] = rdd.sortByKey(ascending = false, numPartitions = 4)
    println(rdd3.getNumPartitions)
    val result3:Array[Array[(Char, Int)]] = rdd3.glom().collect()
    result3.foreach(arr=>println(arr.mkString(",")))
    //按照key的升序排序，生成的RDD的分区数设置为1，就实现了全局有序
    val result4:Array[(Char, Int)] = rdd.sortByKey(ascending = true,numPartitions = 1).collect()
    println(result4.mkString(","))
    sc.stop()
  }
}
