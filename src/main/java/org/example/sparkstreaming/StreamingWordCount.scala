package org.example.sparkstreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object StreamingWordCount {
  def main(args: Array[String]): Unit = {
    //初始化SparkConf类的对象
    val conf:SparkConf = new SparkConf().setAppName("StreamingWordCount").setMaster("local[*]")
    //创建StreamingContext对象
    val ssc =new StreamingContext(conf,Seconds(10)) // 10秒一个批次
    //通过监控node1的9999端口创建DStream对象
    val lines:ReceiverInputDStream[String] = ssc.socketTextStream("node1",9999)
    //将每一行数据做切分，形成一个个单词
    val words:DStream[String] = lines.flatMap(_.split(" "))
    //word=>(word,1)
    val wordOne:DStream[(String,Int)] = words.map((_,1))
    //将相同的key的value做聚合加
    val wordCount:DStream[(String,Int)] = wordOne.reduceByKey(_+_)
    //打印
    wordCount.print()
    //启动
    ssc.start()
    //等待执行停止
    ssc.awaitTermination()
  }
}
