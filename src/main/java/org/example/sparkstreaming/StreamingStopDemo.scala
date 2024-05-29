package org.example.sparkstreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * 此对象演示了如何配置Spark Streaming上下文以优雅地停止应用程序。
 * 它设置了停止时的优雅关闭行为，并演示了如何使用checkpoint来恢复上下文。
 */
object StreamingStopDemo {

  /**
   * 创建并配置一个StreamingContext。
   *
   * @return 配置好的StreamingContext实例。
   */
  def createSSC():StreamingContext = {
    // 初始化Spark配置，设置为本地模式并指定应用名称
    val conf = new SparkConf().setMaster("local[*]").setAppName("StreamingStopDemo")
    // 设置优雅关闭属性
    conf.set("spark.streaming.stopGracefullyOnShutdown", "true")
    // 创建StreamingContext，设置批处理时间为5秒，并配置checkpoint目录
    val ssc: StreamingContext = new StreamingContext(conf, Seconds(5))
    ssc.checkpoint("file:///BIgdate/bigdateinfo/spark/软件/data/cpt")
    ssc
  }

  /**
   * 主函数：启动Spark Streaming应用程序。
   *
   * @param args 命令行参数（未使用）
   */
  def main(args: Array[String]): Unit = {
    // 尝试获取已有的StreamingContext或根据createSSC()创建新的
    val ssc:StreamingContext = StreamingContext.getActiveOrCreate("file:///BIgdate/bigdateinfo/spark/软件/data/cpt", () => createSSC())
    // 启动一个新线程来处理StreamingStop逻辑
    new Thread(new StreamingStop(ssc)).start()
    // 创建一个从指定主机和端口读取数据的输入流
    val line:ReceiverInputDStream[String] = ssc.socketTextStream("node1",9999)
    // 打印接收到的每条线
    line.print()
    // 启动StreamingContext并等待其终止
    ssc.start()
    ssc.awaitTermination()
  }
}
