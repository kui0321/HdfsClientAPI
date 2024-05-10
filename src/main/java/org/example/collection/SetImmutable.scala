package org.example.collection

object SetImmutable extends App {
  val set1 = Set[Int](1,9,2,4)
  val set2 = Set(5,7,8,9,9)
  println(set2)
  val set3 = set1 + 5 + 6
  println(set3)
  val set4 = set2 ++ set3.+(3,4,5)
  println(set4)
}
