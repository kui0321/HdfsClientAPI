package org.example.sparkstreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object CustomerSource {
  def main(args: Array[String]): Unit = {
    //初始化Spark配置信息
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("CustomerSource")
    //初始化
    val ssc = new StreamingContext(sparkConf, Seconds(5))
    //创建自定义receiver的Streaming
    val lines = ssc.receiverStream(new ReceiverCustomer("node1",9999))
    lines.print()
    ssc.start()
    ssc.awaitTermination()
  }
}
