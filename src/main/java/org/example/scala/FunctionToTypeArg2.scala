package org.example.scala

object FunctionToTypeArg2 {
  //一个参数无返回值
  def main(args: Array[String]): Unit = {
    def funcA(fun:String=>Unit):Unit={
      fun("GTJin")
    }
    def funArg(name:String):Unit = {
      println(name)
    }
    funcA(funArg)
    //如果函数funArg只是被调用一次，可以进行简化
    //首先省略def、函数名、返回值类型
    funcA((name:String)=>{
      print(name)
    })
    //函数体只有一行省略大括号
    funcA((name:String)=>println(name))
    //参数类型可以推导出来，也可以省略
    funcA((name)=>println(name))
    //只有一个参数，小括号也可以省略
    funcA(name=>println(name))
    //还可以继续省略，以后在spark的代码中会遇到类似的用法
    funcA(println(_))
    //匿名函数的至简原则的最简化版
    //该方式调用的不再是匿名函数的简化版了，而是调用println函数
    funcA(println)
  }
}
