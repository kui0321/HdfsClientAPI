package org.example.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object CreateByWholeTextFiles {
  def main(args: Array[String]): Unit = {
    val path = "file:///D:\\Javademo\\HdfsClientAPI\\src\\main\\java\\org\\example\\spark\\"
    //构建Sparkconf对象，设置本地运气和文件名
    val conf = new SparkConf().setMaster("local[*]").setAppName("WholeTextFiles")
    val sc = new SparkContext(conf)
    val rdd:RDD[(String,String)] = sc.wholeTextFiles(path)
    val tuple:Array[(String,String)] = rdd.collect()
    tuple.foreach(ele=>println(ele._1,ele._2))
    //获取小文件中的内容
    val array:Array[String] = rdd.map(_._2).collect()
    println(array.mkString("|"))
    sc.stop()
  }
}
