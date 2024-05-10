package org.example.scala

object FunctionNoName {
  def main(args: Array[String]): Unit = {
    def funcA(fun:Int =>Int):Int={
      fun(3)
    }
    //使用匿名函数作为参数
    println(funcA((x:Int)=>{x*3}))
    //类型可以推导出来所以可以省略
    println(funcA((x)=>{x*3}))
    //只有一个参数小括号可以省略
    println(funcA(x=>{x*3}))
    //函数体只有一行，{}可以省略
    println(funcA(x=>x*4))
    //使用_进行简化
    println(funcA(_*5))
    //在funcA内部使用fun(3)不够灵活，再次进行完善
    def funcB(x:Int,fun:Int=>Int):Int ={
      fun(x)
    }
    println(funcB(2,_*5))
  }
}
