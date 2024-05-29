package org.example.spark.UDF

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

import java.text.SimpleDateFormat
import java.util.Calendar
import scala.collection.mutable.ListBuffer

object VpnLogDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("VpnLogDemo")
    val spark = SparkSession.builder().config(conf).getOrCreate()
    spark.sparkContext.setLogLevel("Error")
    val df:DataFrame = spark.read.json("file:///BIgdate\\bigdateinfo\\spark\\软件\\data\\demo\\vpnlog.json")
    df.createTempView("vpnlog")
    spark.sql(
      """
        |select username,replace(replace(ts,'T',' '),".000Z","") as Ts,type
        |,row_number() over(partition by username order by ts) as rn
        |from vpnlog
        |""".stripMargin).createTempView("temp1")
    spark.sql(
      """
        |select a.username nm1,a.ts ts1,a.type ty1,a.rn rn1,b.username nm2,b.ts ts2,b.type ty2,b.rn rn2
        |from temp1 a full outer join temp1 b
        |on a.username = b.username
        |and a.rn = b.rn-1
        |order by a.username, a.ts
        |""".stripMargin).createTempView( "temp2")
    spark.sql(
      """
        |select *
        |from (select
        |case when nm1 is null then nm2 else nm1 end as nm1,
        |case when ts1 is null then concat(split(ts2,' ')[0], ' 00:00:00') else ts1 end as ts1,
        |case when ty1 is null then 'login' else ty1 end ty1,
        |case when nm2 is null then nm1 else nm2 end as nm2,
        |case when ts2 is null then concat(split(ts1,' ')[0], ' 00:00:00') else ts2 end as ts2,
        |case when ty2 is null then 'logout' else ty2 end ty2
        |from temp2) tb_temp
        |where ty1 = 'login'
        |and ty2 = 'logout'
        |""".stripMargin).createTempView("temp3")
    spark.sql(
      """
        |select
        | nm1 nm,ts1,ts2,(unix_timestamp(ts2,'yyyy-MM-dd HH:mm:ss')-
        | unix_timestamp(ts1,'yyyy-MM-dd HH:mm:ss')) as dur
        |from temp3
        |""".stripMargin).createTempView("temp4")
    spark.sql(
      """
        |select nm,sum(dur) as tol,count(1) as toc, max(dur) as maxdur
        |from temp4
        |group by nm
        |""".stripMargin).show(100,false)

    spark.udf.register("getDur",(ts:String,cont:Int)=>{
      val list = new ListBuffer[String]();
      val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
      val calendar:Calendar = Calendar.getInstance()
      calendar.setTime(sdf.parse(ts))
      for (i <- 0 until cont) {
        list.append(sdf.format(calendar.getTime))
        calendar.add(Calendar.HOUR,1)
      }
      list
    })

    spark.sql(
      """
        |select
        |nm1,ts1,ty1,nm2,ts2,ty2
        |,explode(getDur(ts1,hour(ts2)- hour(ts1))) as tt
        |from temp3
        |""".stripMargin).createTempView("temp5")
    spark.sql(
      """
        |select
        |nm1 nm,from_unixtime(unix_timestamp(tt,'yyyy-MM-dd HH:mm:ss'),'yyyy-MM-dd HH') transtime
        |from temp5
        |order by nm,transtime
        |""".stripMargin).createTempView("temp6")
    spark.sql(
      """
        |select transtime,count(nm)
        |from temp6
        |group by transtime
        |order by transtime desc
        |""".stripMargin).show()
    spark.close()
  }
}
