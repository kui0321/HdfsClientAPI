package org.example.sparkstreaming

import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * Direct API演示程序，用于从Kafka主题中消费数据并进行基本的处理。
 *
 * @param args 命令行参数（未使用）
 */
object DirectAPIDemo {
  /**
   * 主函数，创建Spark Streaming上下文并设置从Kafka直接消费数据的流。
   */
  def main(args: Array[String]): Unit = {
    // 创建Spark配置并设置应用名称和执行模式
    val conf = new SparkConf().setMaster("local[*]").setAppName("DirectAPIDemo")
    // 创建Spark Streaming上下文，设置批处理间隔为5秒
    val ssc = new StreamingContext(conf, Seconds(5))

    // 定义Kafka消费者参数
    val kafkaPara:Map[String,Object] = Map(
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> "node1:9092,node2:9092,node3:9092",
      ConsumerConfig.GROUP_ID_CONFIG -> "itbaizhan",
      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG -> "org.apache.kafka.common.serialization.StringDeserializer",
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG -> "org.apache.kafka.common.serialization.StringDeserializer"
    )

    // 创建直接连接到Kafka的DStream，指定订阅的主题和消费者参数
    val kafkaDStream:InputDStream[ConsumerRecord[String, String]] =
      KafkaUtils.createDirectStream[String,String] (ssc,LocationStrategies.PreferConsistent,
        ConsumerStrategies.Subscribe[String,String](Set("sparkstreaming"), kafkaPara))

    // 从Kafka消息中提取值字段，并将其拆分为单词
    val valueDstream = kafkaDStream.map(_.value())
    // 对拆分后的单词进行计数，并打印结果
    valueDstream.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).print()

    // 启动Streaming上下文并等待所有作业完成
    ssc.start()
    ssc.awaitTermination()
  }
}

