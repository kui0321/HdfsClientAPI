package org.example.scala

object FunctionLazy {
  def main(args: Array[String]): Unit = {
    //函数结果没有使用，则函数不执行；直到使用结果才执行
    def funcA():String ={
      println("funcA被执行了")
      "你好250"
    }
    //当函数返回值被声明为lazy时，函数的执行被推迟，直到我们首次对此取值，该函数才会被执行
    //不会先执行函数，函数在调用时执行
    lazy val nihao  = funcA()
    println("----------------")
    println(nihao) //首次对此取值
    println("================")
  }
}
