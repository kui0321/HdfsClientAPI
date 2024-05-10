package org.example.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 统计网站UV
 */
object CountUV {
  def main(args: Array[String]): Unit = {
    //  构建SparkConf对象，并设置本地运行和程序的名称
    val SparkConf = new SparkConf().setMaster("local[*]").setAppName("CountUV")
    // 构建SparkContext对象
    val sc = new SparkContext(SparkConf)
    // 设置日志级别
    sc.setLogLevel("Error")
    // 读取hdfs文件
    val lines:RDD[String] = sc.textFile("/wordcount/input/pvuvdata")
    //.抽取每行数据的ip地址和网址->ip地址_网址
    lines.map(line=>{
      val data: Array[String] = line.split("\t")
      data(0)+"_"+data(5)
    }).distinct()
      .map(ele=>{(ele.split("_")(1),1)})
      .reduceByKey(_+_)
      .sortBy(tp=>{tp._2},false)
      .foreach(ele=>{println(ele._1+"->"+ele._2)})
    sc.stop()
  }
}
