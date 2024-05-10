package org.example.spark.foreachPartition

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/**
 * 主程序，用于演示如何将RDD保存为文本文件。
 *
 * @param args 命令行参数，第一个参数预期为文件保存路径，若未提供则使用默认路径。
 */
object RddSaveAsTextFile {
  def main(args: Array[String]): Unit = {
    // 设置Spark配置，指定Master为本地模式，应用名为SaveAsTextFile
    val conf = new SparkConf().setMaster("local[*]").setAppName("SaveAsTextFile")
    // 根据配置创建SparkContext
    val sc = new SparkContext(conf)
    // 创建一个包含几个字符串的RDD
    val rdd:RDD[String] = sc.parallelize(List("a", "b", "c", "e", "f", "d"))
    // 将RDD保存为文本文件到指定路径（此处使用了硬编码的路径，而非path变量，仅为示例）
    rdd.saveAsTextFile("file:///Javademo/HdfsClientAPI/src/main/java/org/example/spark/data/output/file")
    // 停止SparkContext
    rdd.saveAsTextFile("hdfs://mycluster/spark/output/file")
    sc.stop()
  }
}
