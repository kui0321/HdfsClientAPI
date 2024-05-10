package org.example.spark.Transformation

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RddJoin {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("join")
    val sc = new SparkContext(conf)
    val rdd1: RDD[(Int, String)] = sc.makeRDD(List((101, "鲁班"), (102, "凯"),
        (103, "程咬金")))
    val rdd2: RDD[(Int, String)] = sc.makeRDD(List((101, "射手"), (102, "战士"),
        (104, "法师")))
    val rdd1j2:RDD[(Int,(String,String))] = rdd1.join(rdd2)
    println(rdd1j2.collect().mkString(","))
    val rddlef1:RDD[(Int,(String,Option[String]))] = rdd1.leftOuterJoin(rdd2)
    println(rddlef1.collect().mkString(","))
    val rdd1roj2:RDD[(Int,(Option[String],String))] = rdd1.rightOuterJoin(rdd2)
    println(rdd1roj2.collect().mkString(","))
    println(rdd2.leftOuterJoin(rdd2).collect().mkString(","))
    sc.stop()
  }
}
