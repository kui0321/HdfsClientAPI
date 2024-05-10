package org.example.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object CorePipeline {
  def main(args: Array[String]): Unit = {
    // 构建SparkConf对象，并设置本地运行和程序的名称
    val conf = new SparkConf().setAppName("CorePipeline").setMaster("local[*]")
    // 构建SparkContext对象
    val sc = new SparkContext(conf)
    //读取文件，并生成RDD对象
    val fileRDD = sc.textFile("/wordcount/input/wc.txt")
    /*
      flatMap函数，将RDD中的每个元素拆分成多个元素
      flatMap函数的输入参数是一个函数，函数的输入参数是RDD中的每个元素，函数的输出参数是RDD中的每个元素拆分成的元素
      flatMap函数的输出参数是RDD对象
     */
    def flatMapFunc(line: String): TraversableOnce[String] = {
      println(line)
      line.split(" ")
    }
    // 将RDD中的每个元素拆分成多个元素
    val wordsRdd:RDD[String] = fileRDD.flatMap(flatMapFunc)
    // 将RDD中的每个元素拆分成多个元素,并进行标记
    def mapFunc(word: String): (String, Int) = {
      (word, 1)
    }
    val wordCountRdd:RDD[(String, Int)] = wordsRdd.map(mapFunc)
    // 对拆分的内容进行聚合计算
    val resultRdd:RDD[(String, Int)] = wordCountRdd.reduceByKey(_+_)
    //
    val result:Array[(String, Int)] = resultRdd.collect()
    result.foreach(println)
  }
}
