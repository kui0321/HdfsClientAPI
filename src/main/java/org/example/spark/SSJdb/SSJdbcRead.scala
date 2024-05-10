package org.example.spark.SSJdb

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

import java.util.Properties

object SSJdbcRead {
  def main(args: Array[String]): Unit = {
    //创建上下文环境配置对象
    val conf = new SparkConf().setMaster("local").setAppName("SSJdbcRead")
    //创建执行环境入口对象SparkSession对象
    val spark = SparkSession.builder().config(conf).getOrCreate()
    //通过jdbc读取mysql数据库test中的my_score
    val prop:Properties = new Properties()
    prop.put("user","root")  //用户名
    prop.put("password","123456")  //密码
    prop.put("driver","com.mysql.jdbc.Driver")   //驱动
    //读取数据
    val df = spark.read.jdbc("jdbc:mysql://node1:3306/hive?useSSL=false&useUnicode=true&characterEncoding=utf8", "TABLE_PARAMS", prop)
    //输出数据
    df.printSchema()
    df.show()
    spark.stop()
  }
}
