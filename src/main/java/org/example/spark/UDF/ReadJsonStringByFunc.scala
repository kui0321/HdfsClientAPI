package org.example.spark.UDF

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object ReadJsonStringByFunc {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("ReadJsonStringByFunc")
    val spark = SparkSession.builder().config(conf).getOrCreate()
    val jsonList = List[String] (
      "{\"name\":\"zhangsan\",\"age\":18}",
    "{\"name\":\"lisi\",\"age\":19}",
    "{\"name\":\"wangwu\",\"age\":20}",
    "{\"name\":\"zhangsan\",\"age\":18}",
    "{\"name\":\"maliu\",\"age\":21}"
    )
    import spark.implicits._
    val df = jsonList.toDF("info") //创建临时视图
    df.createTempView("tb_json") //注册临时视图
    spark.sql(
      """
        |select info, get_json_object (info,"$.name" ) as name
        |,get_json_object(info,"$.age") as age
        |from tb_json
        |""".stripMargin).show()
    spark.close()
  }
}
