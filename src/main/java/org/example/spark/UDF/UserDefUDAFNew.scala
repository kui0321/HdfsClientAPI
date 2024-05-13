package org.example.spark.UDF

import org.apache.spark.SparkConf
import org.apache.spark.sql.expressions.{Aggregator, MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.{DataFrame, Encoder, Encoders, Row, SparkSession, functions}

object UserDefUDAFNew {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("UserDefUDAFNew")
    val spark = SparkSession.builder().config(conf).getOrCreate()
    val df:DataFrame = spark.read.json("file:///BIgdate\\bigdateinfo\\spark\\软件\\data\\sql\\score.json")// 读取json文件
    df.createOrReplaceTempView("tb_score") // 注册临时视图
    spark.udf.register("my_avg",functions.udaf(new MyUDAF())) // 注册自定义UDAF函数
    //调用自定义的udaf函数查询每个科目，以及该科目的平均分
    spark.sql(
      """
        |select
        |project,my_avg(score) as avg_score
        |from tb_score
        |group by project
        |""".stripMargin).show()
    spark.close()
  }
}
//自定义样例类
case class MyBuf(var sum:Int,var count:Int)
//自定义类MyUDAF
class MyUDAF() extends Aggregator[Int,MyBuf,Double]{
  //赋初始化的值 0,0
  override def zero: MyBuf = MyBuf(0,0)
  //map端将每个分区下同一组数据进行聚合操作
  override def reduce(b: MyBuf, a: Int): MyBuf = {
    b.sum += a
    b.count += 1
    b
  }
  //reduce端将同一个分组的数据进行聚合
  override def merge(b1: MyBuf, b2: MyBuf): MyBuf = {
    b1.sum += b2.sum
    b1.count += b2.count
    b1
  }
  //聚合后每组数据得到一个MyBuf对象，然后[再做最后的计算] 并返回结果
  override def finish(reduction: MyBuf): Double = reduction.sum.toDouble/reduction.count
  //中间结果的序列化,元祖或样例类调用
  override def bufferEncoder: Encoder[MyBuf] = Encoders.product
  override def outputEncoder: Encoder[Double] = Encoders.scalaDouble
  //最终结果的序列化
}
