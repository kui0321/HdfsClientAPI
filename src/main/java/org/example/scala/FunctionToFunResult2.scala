package org.example.scala

object FunctionToFunResult2 {
  def main(args: Array[String]): Unit = {
    //TODO 2.将函数作为返回值返回，一般应用在将内部的函数在外部使用
    def outher1():()=>Unit={
      def inner():Unit={
        println("inner1....")
      }
      inner _
    }
    outher1()()

    //TODO 3.如果层次多了，就比较麻烦了，这种方式不推荐自定义开发中使用
    def outer(): () => () => Unit = {
      def mid(): () => Unit = {
        def inner(): Unit = {
          println("innrt...")
        }
        inner _
      }
      mid _
    }
    outer()()()
  }
}
