package org.example.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object WordCount {
  def main(args: Array[String]): Unit = {
    //Todo 1 构建SparkConf对象，并设置本地运行和程序的名称
    val conf = new SparkConf().setMaster("local[*]").setAppName("WordCount")
    //todo 2 通过SparkConf对象构建SparkContext对象
    val sc = new SparkContext(conf)
    //TOdo 3 读取文件并生成RDD对象
    val fileRDD:RDD[String] = sc.textFile("/wordcount/input/wc.txt")
    //TODO 4 将单词进行切割，得到一个存储全部单词的集合对象
    val wordsRDD:RDD[String] = fileRDD.flatMap(_.split(" "))
    //TODO 5 将单词转换为Tuple2对象（"hello"-> ("hello",1)）
    val wordAndOneRDD:RDD[(String,Int)] = wordsRDD.map((_,1))
    //TODO 6 将元祖的value按照key进行分组，并对该组所有的value进行聚合操作
    val resultRDD:RDD[(String,Int)] = wordAndOneRDD.reduceByKey(_+_)
    //Todo 7 通过collect方法收集RDD数据
    val wordCount:Array[(String, Int)] = resultRDD.collect()
    //输出结果
    wordCount.foreach(println)
  }
}
