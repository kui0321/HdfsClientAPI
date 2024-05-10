package org.example.scala

object FunctionDef4 {
  def main(args: Array[String]): Unit = {
    def funD1(name:String,score:Double):String = {
      val value = s"有参数有返回值,Hello${name},your score is ${score}"
      value
    }
    println(funD1("zhangs",600))

    def funcD2(name: String,score:Double):String = {
      s"有参数有返回值,Hello ${name},yourscore is ${score}"
    }

    println(funcD2("name2",300))
  }
}
