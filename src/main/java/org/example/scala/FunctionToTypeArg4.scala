package org.example.scala

object FunctionToTypeArg4 {
  def main(args: Array[String]): Unit = {
    def funcA(s:Int,y:Int,fun:(Int,Int)=>Int):Unit={
      val result = fun(s, y)
      println(result)
    }
    funcA(2,4,(s:Int,y:Int)=>s+y)
    funcA(2,4,(x,y)=>x+y)
    funcA(4,8,_*_)
    funcA(10,10,_/_)
  }
}
