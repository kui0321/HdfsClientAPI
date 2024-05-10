package org.example.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object CreateByTextFile {
  def main(args: Array[String]): Unit = {
    val path = "file:///D:\\Javademo\\HdfsClientAPI\\src\\main\\java\\org\\example\\spark\\words.txt"
    // 构建SparkConf对象，并设置本地运行和文件名
      val conf = new SparkConf().setMaster("local[*]").setAppName("textFile")
      //3.通过conf创建SparkContext对象
      val sc = new SparkContext(conf)
      val hdfsRDD:RDD[String] = sc.textFile("/wordcount/input/wc.txt",4)
    val hdfsResule:Array[String] = hdfsRDD.collect()
    println(hdfsRDD.getNumPartitions)
    println(hdfsResule.mkString(","))
    //默认分区数
    val localRDD1:RDD[String] = sc.textFile(path)
    println(localRDD1.getNumPartitions)
    println(localRDD1.collect().mkString(","))
    //设置最小分区数
    val localRDD2:RDD[String] = sc.textFile(path,3)
    println(localRDD2.getNumPartitions)
    println(localRDD2.collect().mkString(","))
    //最小分区数设置是一个参考值，Spark会有自己的判断，值太大Spark不会理会
    val localRDD3:RDD[String] = sc.textFile(path,100)
    println(localRDD3.getNumPartitions)
    println(localRDD3.collect().mkString(","))
  }
}
