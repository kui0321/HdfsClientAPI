package org.example.sparkstreaming

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object TransformDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("TransformDemo")
    val ssc = new StreamingContext(conf, Seconds(5))
    //通过监控node1的9999端口创建DStream对象
    val lines:ReceiverInputDStream[String]  = ssc.socketTextStream("node1",9999)
    val result:DStream[(String, Int)] = lines.transform(rdd => {
      println("***********************")
      val rdd1:RDD[(String, Int)] = rdd.flatMap(_.split(" ")).map(line=>(line,1))
      rdd1.reduceByKey(_+_)
     })
    result.print()
    //启动
    ssc.start()
    //等待执行停止
    ssc.awaitTermination()
  }
}
