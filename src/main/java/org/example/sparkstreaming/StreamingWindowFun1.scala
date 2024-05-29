package org.example.sparkstreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Durations, Seconds, StreamingContext}

object StreamingWindowFun1 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("StreamingWindowFun1")
    //创建StreamingContext对象 数据批处理的间隔时间为5S
    val ssc = new StreamingContext(conf,Seconds(5))
    //通过监控node1的9999端口创建DStream对象
    val lines:ReceiverInputDStream[String] = ssc.socketTextStream("node1",9999)
    //将每一行数据做切分，形成一个个单词
    val wordsDs:DStream[String] = lines.flatMap(_.split(" "))
    //word=>(word,1)
    val wordOne:DStream[(String,Int)] = wordsDs.map((_,1))
    //6.将相同的key的value做聚合加
    //窗口的长度和滑动窗口间隔必须是批处理的间隔时间整数倍
    val wordCount:DStream[(String,Int)] = wordOne.reduceByKeyAndWindow(
        (v1:Int,v2:Int) =>{v1 + v2},
        Durations.seconds(30), //窗口的长度
        Durations.seconds(10)  //滑动窗口间隔
    )
    //打印输出
    wordCount.print()
    //启动
    ssc.start()
    //等待执行停止
    ssc.awaitTermination()

  }
}
