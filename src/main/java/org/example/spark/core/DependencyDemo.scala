package org.example.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object DependencyDemo {
  def main(args: Array[String]): Unit = {
    //构建Saprkconf对象，构建SparkConf对象，并设置本地运行和
    val conf = new SparkConf().setMaster("local[*]").setAppName("DependencyDemo")
    //通过SparkConf对象构建SparkContext对象
    val sc = new SparkContext(conf)
    //读取文件
    val fileRDD:RDD[String] = sc.textFile("/wordcount/input/wc.txt")
    //将单词进行切割, 得到一个存储全部单词的集合对象
    val wordsRDD:RDD[String] = fileRDD.flatMap(_.split(" "))
    println(wordsRDD.dependencies)
    //将单词转换为元组对象, key是单词,value是数字1
    val wordAndOneRdd:RDD[(String, Int)] = wordsRDD.map((_, 1))
    println(wordAndOneRdd.dependencies)
    //将元组的value 按照key来分组, 对所有的value执行聚合操作(相加)
    val resultRdd:RDD[(String, Int)] = wordAndOneRdd.reduceByKey(_+_)
    println(resultRdd.dependencies)
    //通过collect方法收集RDD的数据
    val wordcount:Array[(String, Int)] = resultRdd.collect()
    //打印输出结果
    wordcount.foreach(println)
    sc.stop()
  }
}
