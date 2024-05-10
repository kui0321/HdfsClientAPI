package org.example.spark.Transformation

import org.apache.spark.rdd.RDD
import org.apache.spark.{Partitioner, SparkConf, SparkContext}

object RddPartitionBy {
  def main(args: Array[String]): Unit = {
    //构建SparkConf对象，并设置本地运行和程序名称
    val conf = new SparkConf().setMaster("local[*]").setAppName("partitionBy")
    //使用conf对象构建SparkContet对象
    val sc = new SparkContext(conf)
    //创建1个RDD对象
    val rdd: RDD[(String, Int)] = sc.parallelize(List(("andy", 1), ("jack", 1), ("hello", 1), ("lucy", 1), ("tom", 1),
        ("su", 1)))
    println(rdd.getNumPartitions)
    //调用partitionBy()对rdd对象进行重新分区
    val rdd1:RDD[(String, Int)] = rdd.partitionBy(new Partitioner {
      override def numPartitions: Int = 3
      override def getPartition(key: Any): Int = {
        //获取key的首字母
        val firstChar:Char = key.toString.charAt(0)
        if (firstChar >= 'a' && firstChar <= 'i'){
          0
        }else if (firstChar >= 'j' && firstChar <= 'q'){
          1
        }else {
          2
        }
      }
    })
    val result: Array[Array[(String, Int)]] = rdd1.glom().collect()
    //输出
    result.foreach(arr=>println(arr.mkString(",")))
    //关闭sc对象
    sc.stop()
  }
}
