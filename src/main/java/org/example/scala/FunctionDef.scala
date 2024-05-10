package org.example.scala

object FunctionDef {
  def main(args: Array[String]): Unit = {
    //1.定义无参数无返回值
    def funA():Unit ={
      println("无参数无返回值")
    }

    //调用无参数的函数可以只是用函数名进行调用
    funA()

    //如果函数体只有一行，可以将 {} 省略不写
    def funB():Unit = println("无参数无返回值{} 省略不写")

    funB()
  }
}
