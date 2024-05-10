package org.example.spark.DataFrame


import org.apache.spark.SparkConf
import org.apache.spark.sql.{Dataset, SparkSession}

object CreateDataSet {
  def main(args: Array[String]): Unit = {
    //1.创建配置文件对象
    val conf = new SparkConf().setMaster("local[*]").setAppName("CreateDataSet")
    //2.创建SparkSession对象
    val spark: SparkSession= SparkSession.builder().config(conf).getOrCreate()
    //4.添加隐式转换
    import spark.implicits._
    //5.使用基本类型序列对象创建Dataset对象
    val ds1:Dataset[Int] = Seq(3,6,9,12).toDS()
    //6.输出ds1的结构和数据
    ds1.printSchema()
    ds1.show()
    //7.使用样例类序列创建Dataset
    val ds2:Dataset[Student] = Seq(Student(1,"zhangsan",20),Student(2,"lisi",30))
      .toDS()
    ds2.printSchema()
    ds2.show()
    //8.使用样例类List创建Dataset
    val ds3:Dataset[Student] = List(Student(3,"wangwu",40),Student(4,"zhaoliu",50)).toDS()
    ds3.printSchema()
    ds3.show()
    spark.stop()
  }
}
