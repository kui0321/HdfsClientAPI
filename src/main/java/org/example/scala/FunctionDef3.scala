package org.example.scala

object FunctionDef3 {
  def main(args: Array[String]): Unit = {
    //定义 无参数有返回值
    def funcC1():String = {
      return "无参数有返回值 value1"
    }
    println(funcC1())
    //返回值前面的return可以省略
    def funC2():String = {
      "无参数有返回值 value2"
    }
    println(funC2())
  }
}
