package org.example.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object LineageDemo {
  def main(args: Array[String]): Unit = {
    //构建SparkConf对象，并设置本地运行和程序的名称
    val conf = new SparkConf().setMaster("local[*]").setAppName("LineageDemo")
    //通过SparkConf对象构建SparkContext对象
    val sc = new SparkContext(conf)
    //读取文件
    val fileRdd:RDD[String] = sc.textFile("/wordcount/input/wc.txt")
    println("----------------------00---------------------- ")
    println(fileRdd.toDebugString)
    //将单词进行切割，得到一个存储全部单词的集合对象
    val wordsRDD:RDD[String] = fileRdd.flatMap(_.split(" "))
    println(wordsRDD.toDebugString)
    //将单词转换为Tuple2对象（"hello"->("hello",1)）
    val wordAndOneRdd:RDD[(String, Int)] = wordsRDD.map((_,1))
    println(wordAndOneRdd.toDebugString)
    //将元祖的value按照key进行分组，并对该组所有的value进行聚合操作
    val resultRdd:RDD[(String,Int)] = wordAndOneRdd.reduceByKey(_+_)
    println(resultRdd.toDebugString)
    //通过collect方法收集RDD数据
    val wordCount:Array[(String, Int)] = resultRdd.collect()
    println(wordCount.mkString(","))
    sc.stop()
  }
}
