package org.example.scala

import scala.util.control.Breaks

object FunctionControlAbstract {
  def main(args: Array[String]): Unit = {
    //TODO 1. 对比匿名函数方式
    //函数类型： ()=>Unit
    def funcA(opt:() =>Unit):Unit={
      opt()
    }
    funcA(()=>{
      println("函数控制抽象入门1")
    })
    //TODO 2.函数控制抽象
    //参数类型不完整，在传递参数时也是不完整的；只传递代码就可以了，不需要完整的声明。
    def funcB(opt: =>Unit):Unit={
      opt
    }
    funcB{
      println("函数控制抽象入门2")
    }
    //TODO 3.可以通过控制抽象设计语法
    Breaks.breakable{
      for (index <- 1 to 5){
        if (index == 5){
          Breaks.break()
        }
        println("index="+index)
      }
    }
  }
}
