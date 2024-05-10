package org.example.scala

object FunctionCurry {
  def main(args: Array[String]): Unit = {
    def funcA():Int = {
      var result:Int =1
      for (i <- 1 to 10){
        result = i
        Thread.sleep(10)
      }
      result
    }
    def funcB():Int ={
      var result:Int = 1
      for (i <- 1 to 20){
        result = i
        Thread.sleep(10)
      }
      result
    }
    def funcC(a: Int, b: Int): Int = {
      a + b
    }
    def funcD(a:Int)(b:Int):Int ={
      a + b
    }
    val start1: Long = System.currentTimeMillis()
    println(funcC(funcA(),funcB()))
    val end1: Long = System.currentTimeMillis()
    println("------------未使用柯里化用时:" + (end1 - start1))
    val start2: Long = System.currentTimeMillis()
    println(funcD(funcA())(funcB()))
    val end2: Long = System.currentTimeMillis()
    println("------------使用柯里化用时:" + (end2 - start2))
  }
}
