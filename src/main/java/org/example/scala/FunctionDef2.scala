package org.example.scala

object FunctionDef2 {
  def main(args: Array[String]): Unit = {
    //一个参数，无返回值
    def funcB1(x:Int):Unit={
      var y1:Int = x.*(2)
      // . 可以省略
      var y2: Int = x * (2)
      //调用的方法如果只有一个参数，()可以省略
      var y3: Int = x * 2
      println(s"有参数无返回值 y1 = ${y1},y2 = ${y2},y3 = ${y3}")  //
    }

    funcB1(3)

    //两个参数 无返回值
    def funcB2(x:Int,y:Int):Unit = {
      var z = x + y
      println(s"有2个参数无返回值 z=${z}")
    }

    funcB2(1,2)
  }
}
