package org.example.scala

object FunctionRecursion2 {
  def main(args: Array[String]): Unit = {
    def funcB():Unit = {
      println("funcB")
      funcB()
    }
    funcB()
  }
}
