package org.example.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object CoreCheckpoint {
  def main(args: Array[String]): Unit = {
    //构建SparkConf对象，并设置本地运行和程序的名称
    val conf = new SparkConf().setMaster("local[*]").setAppName("CoreCheckpoint")
   //    通过SparkConf对象构建SparkContext对象
    val sc = new SparkContext(conf)
    //设置检查点目录
    sc.setCheckpointDir("/wordcount/input/checkpoints/")
    // 读取文件
    val fileRdd:RDD[String] = sc.textFile("/wordcount/input/wc.txt")
    val wordsRdd:RDD[String] = fileRdd.flatMap(_.split(" "))
    val wordAndOneRdd:RDD[(String, Int)] = wordsRdd.map((_,1))
    // 对wordAndOneRdd进行检查点存在
    wordAndOneRdd.checkpoint()
    // 对单词进行聚合
    val resultRdd:RDD[(String, Int)] = wordAndOneRdd.reduceByKey(_ + _)
    println(resultRdd.collect().mkString(","))
    //设计另外一个job
    val groupByRdd:RDD[(String, Iterable[Int])] = wordAndOneRdd.groupByKey()
    val groupByValueRdd:RDD[(String, Int)] = groupByRdd.mapValues(_.sum)
    println(groupByValueRdd.collect().mkString(","))
    sc.stop()
  }
}
