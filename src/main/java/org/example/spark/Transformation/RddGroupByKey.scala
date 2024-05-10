package org.example.spark.Transformation

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RddGroupByKey {
  def main(args: Array[String]): Unit = {
    //构建SparkConf对象,并设置本地运行及脚本名称
    val conf = new SparkConf().setMaster("local[*]").setAppName("groupByKey")
    //构建SparkContext
    val sc = new SparkContext(conf)
    //构建RDD对象
    val rdd:RDD[(Char,Int)] = sc.parallelize(Array(('a', 1), ('a', 2),
      ('b', 1), ('b', 2), ('a', 3), ('a', 4)))
    //按照key进行分组，分组后的结果是有二元组组成的RDD
    val gbkRdd:RDD[(Char,Iterable[Int])] = rdd.groupByKey()
    val result1:Array[(Char,Iterable[Int])] = gbkRdd.collect()
    println(result1.mkString(","))
    //使用map转换算子，对value数据转为List
    val result2: Array[(Char, List[Int])] = gbkRdd.map(tup=>(tup._1,tup._2.toList)).collect()
    println(result2.mkString(","))
    //关闭sc对象
    sc.stop()
  }
}
