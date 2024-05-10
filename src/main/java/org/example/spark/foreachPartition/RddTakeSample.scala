package org.example.spark.foreachPartition

import org.apache.spark.{SparkConf, SparkContext}

object RddTakeSample {
  def main(args: Array[String]): Unit = {
    //.构建SparkConf对象，并设置本地运行和程序名称
    val conf = new SparkConf().setMaster("local[*]").setAppName("RddTakeSample")
    //使用conf对象构建SparkContet对象
    val sc = new SparkContext(conf)
    //创建RDD对象
    val rdd = sc.parallelize(List(5, 2, 1, 4, 3, 8, 6, 1))
    //第一个参数为true表示被抽出的元素返回，所以抽样的元素个数可以超过rdd.count() 元素个数
    val result:Array[Int] = rdd.takeSample(true,10, 2)
    println(result.mkString(","))
    //第一个参数为false表示被抽取出的数据不放回
    println(rdd.takeSample(false,10,2).mkString(","))
    //三个参数的值都一样时，多次抽取结果
    println(rdd.takeSample(false,5,3).mkString(","))
    println(rdd.takeSample(false, 5, 3).mkString(","))
  }
}
