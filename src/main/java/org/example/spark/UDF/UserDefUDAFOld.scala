package org.example.spark.UDF

import org.apache.spark.SparkConf
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

object UserDefUDAFOld {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("UserDefUDAFOld")
    val spark = SparkSession.builder().config(conf).getOrCreate()
    //创建数据
    val nameList:List[String] = List("zhangsan","lisi","wangwu","wangwu", "zhangsan", "lisi","zhangsan")
    import spark.implicits._
    //将List对象转换为DataFrame对象
    val frame:DataFrame = nameList.toDF("name")
    //注册临时视图
    frame.createOrReplaceTempView("mytable")
    //注册udaf函数
    spark.udf.register("my_count",new MyCount())
    //调用自定义的函数进行查询操作
    spark.sql(
      """
        |select name,my_count(name) AS cut
        |from mytable
        |group by name
        |""".stripMargin).show()
    spark.close()
  }
}

class MyCount extends UserDefinedAggregateFunction{
  //输出数据的类型结构
  override def inputSchema: StructType = StructType(List[StructField](StructField("name",StringType,true)))
  //在聚合过程中处理的数据类型
  override def bufferSchema: StructType = StructType(List[StructField](StructField("name",IntegerType,true)))
  //最终返回值的类型，和evaluate返回值的类型要一致
  override def dataType: DataType = IntegerType
  //如果此函数是确定性的，即给定相同的输入，则始终返回相同的输出，则返回true。
  override def deterministic: Boolean = true   //
  //作用在map和reduce两侧，为每个分区内的每个分组的数据赋初始化的值0
  override def initialize(buffer: MutableAggregationBuffer): Unit = buffer.update(0,0)
  //作用在map端的每个分区内每个分组数据上进行聚合操作
  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = buffer.update(0,buffer.getInt(0) + 1)
  //作用在reduce端，将每个分区内的每个分组聚合结果进行合并
  override def merge(buffer1: MutableAggregationBuffer, buffer2:Row): Unit = buffer1.update(0,buffer1.getInt(0) + buffer2.getInt(0))
  //聚合之后，每组数据最终结算的结果返回值，类型要和dataType一致
  override def evaluate(buffer: Row): Any = buffer.getInt(0)
}
