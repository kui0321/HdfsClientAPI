package org.example.spark.UDF

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

object UserDefUDF {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("UserDefUDF")
    val spark = SparkSession.builder().config(conf).getOrCreate()
    val nameList = List("zhangsan", "lisi", "wangwu")
    import spark.implicits._   // 导入隐式转换
    val nameDF:DataFrame = nameList.toDF("name")  // 将集合转换为DataFrame
    nameDF.createOrReplaceTempView("students")  // 创建临时视图
    /**
     * 参数1：UDF名称，可被用于SparkSQL的sql语句中
     * 参数2：被注册成UDF的方法
     * 参数3：声明UDF的返回值类型
     */
    spark.udf.register("str_len", (str:String)=>str.length) // 注册UDF 可以省略自定义函数的返回值类型
    spark.sql(
      """
        |select name,str_len(name) as length from students
        |order by length desc
        |""".stripMargin).show()
    spark.close()
  }
}
