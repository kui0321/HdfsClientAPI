package org.example.scala

//闭包
object FunctionClosure {
  def main(args: Array[String]): Unit = {
    def outher(x:Int):(Int)=>Int={
      def inner(y:Int):Int={
        x+y
      }
      inner _
    }
    val fun = outher(2)
    val fun2 = fun(3)
    println(fun2)
  }
}
