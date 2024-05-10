package org.example.spark.SSJdb

import org.apache.spark.SparkConf
import org.apache.spark.sql.{Dataset, SparkSession}

import java.util.Properties

object SSJdbcWrite {
  def main(args: Array[String]): Unit = {
    //1创建上下文环境配置对象
    val conf = new SparkConf().setMaster("local").setAppName("SSJdbcWrite")
    //2.创建执行环境入口对象SparkSession对象
    val spark = SparkSession.builder().config(conf).getOrCreate()
    //3.添加隐式转换
    import spark.implicits._
    val porp: Properties = new Properties()
    porp.put("user","root")
    porp.put("password","123456")
    porp.put("driver","com.mysql.jdbc.Driver")
    val ds:Dataset[Score] = Seq(Score(1,"张三",90),Score(2,"李四",80),Score(3,"王五",70)).toDS() //创建数据集
    ds.write.jdbc("jdbc:mysql://node1:3306/test?useSSL=false&useUnicode=true&characterEncoding=utf8","my_score1",porp)
    spark.stop()
  }
}
