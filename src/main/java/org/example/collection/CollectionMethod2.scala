package org.example.collection

import scala.collection.mutable.ListBuffer

object CollectionMethod2 extends App {
  val list: ListBuffer[Int] = ListBuffer(1,2,3,4)
  println(list.head)
  println(list.tail)
  println(list.tails.mkString(","))
  println(list.last)
  list.append(5)
  println(list.init)
  println(list.inits.mkString("@"))

  val list1:ListBuffer[Int] = ListBuffer(3,4,5,6)
  println(list.union(list1))  //并集
  println(list.intersect(list1))   //交集

  println(list.diff(list1))
  println(list1.diff(list))

  println(list.splitAt(3))
  println(list.sliding(2).mkString("#"))

  println(list.sliding(2,2).mkString("#"))

  println(list.zip(list1))
  println(list.zipWithIndex)

  println(list.max)
  println(list.min)
  println(list.sum)
  println(list.product)


  println(list.reduce(_+_))
  println(list.reduce(_-_))

  println(list.reduceLeft(_+_))
  println(list.reduceLeft(_-_))

  println(list.reduceRight(_+_))
  println(list.reduceRight(_-_))

  println(list.fold(5)(_+_))
  println(list.fold(6)(_-_))
  println(list.foldRight(6)(_+_))
}
