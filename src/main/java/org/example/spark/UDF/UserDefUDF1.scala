package org.example.spark.UDF

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.udf
import org.json4s.scalap.InRule

object UserDefUDF1 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("UserDefUDF1")
    val spark = SparkSession.builder().config(conf).getOrCreate()
    val randomUDFObj:UserDefinedFunction = udf(()=>Math.random())  // 无参数数的自定义函数
    spark.udf.register("random",randomUDFObj)
    val plusOneUDFObj:UserDefinedFunction = udf((x:Int)=> x + 1)  //一个参数的自定义函数
    spark.udf.register("plusOne",plusOneUDFObj)
    spark.udf.register("plusOne2",(_:String).length+(_:Int))   //两个参数的自定义函数
    spark.udf.register("arg_filter",(_:Int)>5)  //用在where语句中的UDF
    spark.range(1,10).createOrReplaceTempView("nums")
    spark.sql("select * from nums where id > random()").show()
    spark.sql("select plusOne(id) from nums").show()
    spark.sql("select plusOne2('hello',plusOne(id)) from nums").show()
    spark.sql("select * from nums where arg_filter(id)").show()
    spark.close()
  }
}
