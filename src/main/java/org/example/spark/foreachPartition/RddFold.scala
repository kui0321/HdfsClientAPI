package org.example.spark.foreachPartition

import org.apache.spark.{SparkConf, SparkContext}

object RddFold {
  def main(args: Array[String]): Unit = {
    //构建SparkConf对象，并设置本地运行和程序名称
    val conf = new SparkConf().setMaster("local[*]").setAppName("RddFold")
    //使用conf对象构建SparkContet对象
    val sc = new SparkContext(conf)
    //.创建RDD对象
    val rdd = sc.parallelize(List(1, 2, 3, 4, 5, 6, 7, 8, 9), 3)
    //6.执行带有初始值的fold聚合操作
    //10表示初始值，_+_表示做聚合加操作
    val result = rdd.fold(10)(_ + _)
    ////7.注意：初始值的类型要和RDD中数据的类型一致
    println(result)
    ////4.关闭sc对象
    sc.stop()
  }
}
