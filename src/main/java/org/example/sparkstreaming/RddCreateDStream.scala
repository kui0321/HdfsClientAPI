package org.example.sparkstreaming

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Seconds, StreamingContext}
import scala.collection.mutable

object RddCreateDStream {
  def main(args: Array[String]): Unit = {
    //1.初始化Spark配置信息
    val conf = new SparkConf().setMaster("local[*]").setAppName("RddCreateDStream")
    //2.初始化SparkStreamingContext
    val ssc = new StreamingContext(conf, Seconds(3))
    //3.创建RDD
    val rddQueue = new mutable.Queue[RDD[Int]]()
    //获取InputDStream，oneAtATime 是否只应使用一个RDD
    val inputDStream = ssc.queueStream(rddQueue, false)
    //处理队列中的RDD数据
    val mapStream = inputDStream.map((_,1))
    val redStream = mapStream.reduceByKey(_ + _)

    //打印结构
    redStream.print()
    //7.启动任务
    ssc.start()
    //循环创建并向RDD队列中放入RDD
    for (i <- 1 to 5) {
      rddQueue += ssc.sparkContext.makeRDD(0 to 10)
      Thread.sleep(2000)
    }
    ssc.awaitTermination()
  }
}
