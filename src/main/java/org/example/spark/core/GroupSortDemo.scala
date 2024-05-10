package org.example.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object GroupSortDemo {
  def main(args: Array[String]): Unit = {
    //1.构建SparkConf对象
    val conf = new SparkConf().setMaster("local[1]").setAppName("GroupSortDemo")
    //2.使用conf对象构建SparkContext对象
    val sc = new SparkContext(conf)
    //3.读取数据
    val lines = sc.textFile("file:///BIgdate/bigdateinfo/spark/软件/data/core/group_data.txt")
    //4.将数据整理成(class,score)的格式
    val pairInfos:RDD[(String,Int)] = lines.map(line => {
      val array:Array[String] = line.split("\t")
      (array(0),array(1).toInt)
    })
    //5.将整理好的数据进行分组，并排序
    pairInfos.groupByKey().foreach(tp=>{
      val key:String = tp._1
      val list:List[Int] = tp._2.toList
      val sortedList:List[Int] = list.sortWith(_>_)
      //6.将分组后的数据进行排序
      if (sortedList.length > 3) {
        for (i <-0 until 3) {
          println(s"class = $key ,score=${sortedList(i)}")
        }
      }else{
        for (score<- sortedList) {
          println(s"class = $key ,score=$score")
        }
      }
    })
    sc.stop()
  }
}
