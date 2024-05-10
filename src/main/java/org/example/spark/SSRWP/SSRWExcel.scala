package org.example.spark.SSRWP

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object SSRWExcel {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("SSRWExcel")
    val spark = SparkSession.builder().config(conf).getOrCreate()
    val df = spark.read.format("com.crealytics.spark.excel")
      .option("header", "true")   //读取excel文件是否使用第一行作为表头
      .option("treatEmptyValuesAsNulls", "true")  //读取excel文件是否将空值作为null值
      .option("inferSchema", "true")   // 读取excel文件是否自动推断数据类型
      .option("addColorColumns", "false")   // 读取excel文件是否添加颜色列
      .option("sheetName", "Sheet1")  // 读取excel文件指定sheet
      .option("startColumn", 0)   // 读取excel文件指定开始列
      .option("endColumn", 0)  // 读取excel文件指定结束列
      .option("endColumn", 0)   // 读取excel文件指定结束行
      .option("dataAddress","A1")  // 读取excel文件指定数据地址
      .load("file:///BIgdate/bigdateinfo/spark/软件/data/sql/student.xlsx")  // 读取excel文件

    df.show()
    spark.stop()
  }
}
