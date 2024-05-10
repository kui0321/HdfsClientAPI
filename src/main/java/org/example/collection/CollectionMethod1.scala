package org.example.collection

object CollectionMethod1 extends App {
  val array:Array[Int] = Array(1,2,3,4,5)
  println(array.length)
  println(array.size)
  println(array.mkString(","))
  println(array.contains(5))
  println(array.isEmpty)
  println(array.iterator)
  println(array.take(2).mkString(","))
  println(array.takeRight(2).mkString(","))
  println(array.drop(2).mkString(","))
  println(array.dropRight(2).mkString(","))
  println(array.reverse.mkString(","))
  println(array.distinct.mkString(","))
  println(array.find(ele => ele % 2 == 0).mkString(","))
  println(array.find(ele => ele % 2 == 1).mkString(","))
}
