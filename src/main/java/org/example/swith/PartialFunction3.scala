package org.example.swith

object PartialFunction3 extends App {
  val list:List[Any] = List(1,2,"gtnio",3,4)
  val list1:List[Int] = list.collect{
    case it:Int => it + 1
  }
  println(list1)

  val it:Int = 8
  val long:Long = it
  println(long)
}
