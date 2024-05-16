package org.example.spark.UDF

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

object ReadNestJsonFile {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("ReadNestJsonFile")
    val spark = SparkSession.builder().config(conf).getOrCreate()
    spark.sparkContext.setLogLevel("ERROR")
    val df:DataFrame = spark.read.json("file:///BIgdate\\bigdateinfo\\spark\\软件\\data\\demo\\nestJsonFile.json")
    //方式一：
    df.createOrReplaceTempView("tb_json")
    spark.sql(
      """
        |select name ,score,infos.age,infos.gender
        |from tb_json
        |""".stripMargin).show(false)
    //方式二：
    import org.apache.spark.sql.functions.col
    df.select(col("name"),col("score"),col("infos.age"),col("infos.gender")).show(false)
    spark.close()
  }
}
