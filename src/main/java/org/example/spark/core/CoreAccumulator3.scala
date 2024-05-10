package org.example.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.util.LongAccumulator
import org.apache.spark.{SparkConf, SparkContext}

object CoreAccumulator3 {
  def main(args: Array[String]): Unit = {
    // 创建SparkConf并设置App名称
    val conf = new SparkConf().setMaster("local[*]").setAppName("CoreAccumulator3")
    // 创建SparkContext，该对象是提交Spark App的入口
    val sc = new SparkContext(conf)
    //创建RDD对象
    val rdd = sc.parallelize(Range(1,26,1).toList,2)
    //创建一个累加器对象
    //累加器可能会出现被重复计算的问题
    val sum:LongAccumulator = sc.longAccumulator("sum")
    //如果担心之前被使用过，怕产生不必要的干扰，可以重置
    sum.reset()
    def mapFunc(data:Int)={
      sum.add(1)
      data
    }
    //使用map算子调用mapFunc
    val rddAcc:RDD[Int] =rdd.map(mapFunc)
    //或者使用如下方式为rddAcc设置缓存
    rddAcc.persist(StorageLevel.DISK_ONLY)
    //第一遍计算累加器
    rddAcc.collect()
    //第二遍计算累加器
    val RDD2:RDD[Int] = rddAcc.map(_ * 10)
    RDD2.collect()
    println(sum.value)
    rddAcc.unpersist()
    //停止sc
    sc.stop()
  }
}
