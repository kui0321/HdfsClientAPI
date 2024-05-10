package org.example.spark.core

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD

case class SecondSortKey(first:Int,second:Int) extends Ordered[SecondSortKey] with Serializable {
  override def compare(that: SecondSortKey): Int = {
    if(this.first - that.first != 0) {
      this.first - that.first
    } else {
      this.second - that.second
    }
  }
}


object SecondSortDemo {
  def main(args: Array[String]): Unit = {
    //1.构建SparkConf对象
    val path = "file:///BIgdate/bigdateinfo/spark/软件/data/core/second_sort.txt"
    val conf = new SparkConf().setMaster("local[*]").setAppName("SecondSortDemo")
    //2.使用conf对象构建SparkContext对象
    val sc = new org.apache.spark.SparkContext(conf)
    //3.读取数据
    val lines:RDD[String] = sc.textFile(path)
    //4.将数据进行整理
    val pairInfo:RDD[(SecondSortKey,String)] = lines.map(line => {
      val arr:Array[String] = line.split("\t")
      val first:Int = arr(0).toInt
      val second:Int = arr(1).toInt
      (SecondSortKey(first, second),line)
    })
    pairInfo.sortByKey(false).foreach(tp => {
      println(tp._2)
    })
    sc.stop()
  }
}
