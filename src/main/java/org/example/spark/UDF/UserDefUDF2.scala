package org.example.spark.UDF

import org.apache.spark.SparkConf
import org.apache.spark.sql.types.{ArrayType, StringType}
import org.apache.spark.sql.{DataFrame, SparkSession}

object UserDefUDF2 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("UserDefUDF2")
    val spark = SparkSession.builder().config(conf).getOrCreate()
    val lineList = List("python java scala", "spark core sql streaming", "wangwu")
    import spark.implicits._
    val lineDF:DataFrame = lineList.toDF("line")
    lineDF.createOrReplaceTempView("words")
    spark.udf.register("split_space",(line:String) => line.split(" "),ArrayType(StringType))
    spark.sql(
      """
        |select line,split_space(line) as arr
        |from words
        |""".stripMargin).show()
    spark.close()
  } 
}
