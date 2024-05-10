package org.example.scala

object FunctionDefaultValue {
  //定义带有参数默认值的函数
  def main(args: Array[String]): Unit = {
    //1.定义时，带有默认值的参数不要在参数列表的最后位置，也就是可以在参数列表的任何位置。
    def funcA(a:Int=10,b:Int)={
      println(a+b)
    }
    funcA(1,2)
    //当固定参数写在前时，需要指定两个参数，写在后时可以省略不传
    def funcB(a: Int, b: Int = 10): Unit = {
      println(a + b)
    }

    funcB(3)
  }
}
