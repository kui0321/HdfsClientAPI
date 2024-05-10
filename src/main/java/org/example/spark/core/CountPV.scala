package org.example.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 统计网站PV
 */
object CountPV {
  def main(args: Array[String]): Unit = {
    //  构建SparkConf对象，并设置本地运行和程序的名称
    val conf = new SparkConf().setMaster("local[*]").setAppName("CountPV")
    // 构建SparkContext对象
    val sc = new SparkContext(conf)
    //设置日志级别
    sc.setLogLevel("Error")
    //读取hdfs文件
    val lines: RDD[String] = sc.textFile("/wordcount/input/pvuvdata")
    //从每行日志中提取信息->(网址,1)
    val pv_www:RDD[(String,Int)] = lines.map(
      lines=>{(lines.split("\t")(5),1)
      }
    ).reduceByKey((v1,v2)=>v1+v2)
      .sortBy(tp=>{tp._2},false)
    pv_www.foreach(pv_www=>{println(pv_www._1+"->"+pv_www._2)})
    //计算城站点地点的数量
    val pv_cen:RDD[(String,Int)] =lines.map(
      lines => {
        (lines.split("\t")(1), 1)
      }
    ).reduceByKey((v1, v2) => v1 + v2)
      .sortBy(tp => {
        tp._2
      }, false)
    pv_cen.foreach(pv_cen=>{println(pv_cen._1+"->"+pv_cen._2)})
    sc.stop()
  }
}
