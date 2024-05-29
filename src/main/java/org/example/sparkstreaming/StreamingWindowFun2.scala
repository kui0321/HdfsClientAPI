package org.example.sparkstreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Durations, Seconds, StreamingContext}

object StreamingWindowFun2 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("StreamingWindowFun2")
    //创建StreamingContext对象 数据批处理的间隔时间为5S
    val ssc = new StreamingContext(conf,Seconds(5))
    ssc.checkpoint("file:///BIgdate/bigdateinfo/spark/软件/data/cpt")
    val lines:ReceiverInputDStream[String] = ssc.socketTextStream("node1",9999)
    //将每一行数据做切分，形成一个个单词
    val wordsDs:DStream[String] = lines.flatMap(_.split(" "))
    //word=>(word,1)
    val wordOne:DStream[(String, Int)] = wordsDs.map((_,1))
    //将相同的key的value做聚合加
    val wordCount:DStream[(String, Int)] = wordOne.reduceByKeyAndWindow(
      (a:Int,b:Int)=>a+b,
      (a:Int,b:Int)=>a-b,
      Durations.seconds(30),
      Durations.seconds(10)
    )
    //打印输出
    wordCount.print()
    //启动
    ssc.start()
    //等待执行停止
    ssc.awaitTermination()
  }
}
