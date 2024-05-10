package org.example.collection

import scala.collection.mutable

object SetMutable extends App {
    val set1 = mutable.Set(1,4,2,5,6,9)
    val set2:mutable.Set[Int] = mutable.Set(2,2,3,4,5,6)
    println(set2)
    set2.add(1)
    println(set2)
    println(set1.mkString(":"))
  set1.update(3,true)
  println(set1)
  set1.update(9,false)
  println(set1)
  val bool1 = set2.remove(2)
  println(set2.mkString(":"),bool1)
  val set4 = set1 & set2
  println(set4)
  val set3:mutable.Set[Int] = set1 &~ set2
  println(set1,set2,set3)
}
