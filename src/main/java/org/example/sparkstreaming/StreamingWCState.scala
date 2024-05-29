package org.example.sparkstreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object StreamingWCState {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("StreamingWCState").setMaster("local[*]")
    val ssc = new StreamingContext(conf, Seconds(5))
    //设置checkpoint
    ssc.checkpoint("file:///BIgdate/bigdateinfo/spark/软件/data/cpt")
    val lines:ReceiverInputDStream[String] = ssc.socketTextStream("node1",9999)
    //将每一行数据做切分，形成一个个单词
    val wordsDS:DStream[String] = lines.flatMap(_.split(" "))
    val wordsOne:DStream[(String, Int)] = wordsDS.map((_, 1))
    ////A2.自定义更新状态的方法，参数values为当前批次单词的频度，state为以往批次单词的频度
    val undateFunc = (values:Seq[Int],state:Option[Int])=>{
      //获取上一批次计算后的结果（状态）
      val preCount:Int = state.getOrElse(0)
      //将本批次的单词数据聚合
      val currentCount:Int = values.foldLeft(0)(_ + _)
      //返回新的状态
      Some(currentCount+preCount)
    }
    //将相同的key的value做聚合加
    val wordCount:DStream[(String, Int)] = wordsOne.updateStateByKey(undateFunc)
    //7.打印输出
    wordCount.print()
    //8.启动
    ssc.start()
    //9.等待执行停止
    ssc.awaitTermination()
  }
}
