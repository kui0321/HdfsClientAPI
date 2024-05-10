package org.example.Implicit

object implicitTransFrom1   {
  def main(args: Array[String]): Unit = {
    implicit def trans(dou:Double): Int = {
      dou.toInt
    }
    def transfrom(implicit dou: Double = 21.2)= {dou.toInt}
    //println(transfrom)

    implicit val score: Double = 89.0
    //println(score)
    println(transfrom())
  }
}
