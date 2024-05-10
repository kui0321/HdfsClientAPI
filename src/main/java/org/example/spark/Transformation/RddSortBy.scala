package org.example.spark.Transformation

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RddSortBy {
  def main(args: Array[String]): Unit = {
    //2.构建SparkConf对象，并设置本地运行和程序名称
    val conf:SparkConf = new SparkConf().setMaster("local[*]").setAppName("SortBy")
    //3.使用conf对象构建SparkContet对象
    val sc = new SparkContext(conf)
    //5.创建Key-Value型Rdd
    val rdd:RDD[(Char, Int)] = sc.parallelize(List(('w', 2), ('h', 5),
      ('k', 9), ('m', 3), ('a', 7),
      ('p', 4), ('q', 1), ('n', 8), ('y',6)))
    //6.使用sortBy对rdd按照元祖的第二个值进行排序
    /*f: (T) => K,指定按照第几个元素记进行排序 ascending: Boolean = true,true表示升序，
    false表示降序，默认就是true升序 numPartitions: Int = this.partitions.length排序的分区数，默认为rdd的
    分区数*/
    val result1:Array[Array[(Char,Int)]] = rdd.sortBy(tup=>tup._2,ascending = true, numPartitions = 3).glom().collect()
    result1.foreach(arr=>println(arr.mkString(",")))
    //全局有序，排序后的分区数设置为1
    val result2:Array[(Char, Int)] = rdd.sortBy(tub=>tub._2,ascending = true, numPartitions = 1).collect()
    println(result2.mkString(","))
    //按照元祖的第一个元素进行降序排序
    val result3:Array[(Char, Int)] = rdd.sortBy(tub=>tub._1,ascending = false, numPartitions = 1).collect()
    println(result3.mkString(","))
    //关闭sc对象
    sc.stop()
  }
}
