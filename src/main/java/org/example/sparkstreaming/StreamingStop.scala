package org.example.sparkstreaming

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.streaming.{StreamingContext, StreamingContextState}

import java.net.URL

/**
 * 此类用于实现一个监控机制，以便在接收到停止信号时安全地停止Spark Streaming上下文。
 *
 * @param ssc StreamingContext对象，代表Spark Streaming的上下文。
 */
class StreamingStop(ssc:StreamingContext) extends Runnable{

  /**
   * 该方法是Runnable接口的实现，用于在新线程中运行，定期检查是否应该停止StreamingContext。
   */
  override def run(): Unit = {
    // 获取Hadoop文件系统实例，用于检查停止信号文件
    val fs:FileSystem = null//FileSystem.get(new URL("hdfs://node2:9820"),new Configuration(),"root")

    while (true) {
      // 尝试让线程休眠5秒，以便定期检查
      try
        Thread.sleep(5000)
      catch {
        case e:InterruptedException => e.printStackTrace() // 如果线程在休眠中被中断，打印堆栈跟踪
      }

      // 获取当前StreamingContext的状态
      val state:StreamingContextState = ssc.getState()

      // 如果StreamingContext处于活动状态，则检查停止信号文件是否存在
      if (state == StreamingContextState.ACTIVE) {
        // 检查"hdfs://node2:9820/stopSpark"路径是否存在，代表停止信号
        val bool:Boolean = fs.exists(new Path("hdfs://node2:9820/stopSpark"))
        if (bool) {
          // 如果存在停止信号文件，则安全停止StreamingContext并退出程序
          ssc.stop(stopSparkContext = true, stopGracefully = true)
          System.exit(0) // 退出JVM
        }
      }
    }
  }
}
