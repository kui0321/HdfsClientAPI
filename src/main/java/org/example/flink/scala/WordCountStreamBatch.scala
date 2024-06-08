package org.example.flink.scala

import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

object WordCountStreamBatch {
  def main(args: Array[String]): Unit = {
    import org.apache.flink.api.scala._
    val env:StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    env.setRuntimeMode(RuntimeExecutionMode.BATCH)
    val dataSource:DataStream[String] = env.readTextFile("D:\\BIgdate\\bigdateinfo\\Flink\\软件\\datas\\words")
    dataSource.flatMap(_.split("\\s+"))
      .map((_,1))
      .keyBy(_._1)
      .sum(1)
      .print()
    env.execute("WordCountStreamBatch")
  }
}
