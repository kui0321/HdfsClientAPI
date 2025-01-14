package org.example.sparkstreaming

import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.receiver.Receiver

import java.io.{BufferedReader, InputStreamReader}
import java.net.Socket
import java.nio.charset.StandardCharsets

class ReceiverCustomer(host:String,port:Int) extends Receiver[String](StorageLevel.MEMORY_ONLY){
  //最初启动的时候，调用该方法
  //作用：读数据并将数据发送给Spark
  override def onStart(): Unit = {
    new Thread("Socket Receiver"){
      override def run(): Unit = {
        receive()
      }
    }.start()
  }
  override def onStop(): Unit = {}
  //读数据并将数据发送给Spark
  def receive(): Unit = {
    ////创建一个Socket
    var socket:Socket = new Socket(host, port)
    //定义一个变量，用来接收端口传过来的数据
    var input:String = null
    //定义一个变量，用来接收端口传过来的数据
    val reader = new BufferedReader(new InputStreamReader(socket.getInputStream,StandardCharsets.UTF_8))
    //读取数据
    input = reader.readLine()
    //当receiver没有关闭并且输入数据不为空，则循环
    //发送数据给Spark
    while (!isStopped() && input != null){
      store(input)
      input = reader.readLine()
    }
    reader.close()
    socket.close()
    //重启任务
    restart("restart")
  }
}
