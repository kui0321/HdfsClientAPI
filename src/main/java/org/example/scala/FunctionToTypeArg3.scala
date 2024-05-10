package org.example.scala

object FunctionToTypeArg3 {
  def main(args: Array[String]): Unit = {
    def funcA(fun:(Double,Double)=>Double):Double={
      fun(2.0,4.0)
    }
    def funcB(x:Double,y:Double):Double={
      x/y
    }
    println(funcA(funcB))
    println(funcA((x:Double,y:Double)=>{
      x/y
    }))
    println(funcA((x,y)=>{
      x/y
    }))
    println(funcA((x,y)=>x/y))
    //顺序使用且只用一次可以简化为下划线
    println(funcA(_*_))
    //但是如果不是顺序使用，无法使用下划线简化，比如：结果为2 不是0 .5
    println(funcA((x,y)=>y/x))
  }
}
