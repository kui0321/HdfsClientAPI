package org.example.collection

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object ArrayTransformer extends App {
  val buffer: ArrayBuffer[Int] = ArrayBuffer(1, 2, 3)

  val array:Array[Int] = buffer.toArray
  println(array.mkString(","))

  val buffer1: mutable.Buffer[Int] = array.toBuffer
  println(buffer1.mkString(","))

  val list:List[Int] = array.toList
  val list1:List[Int] = buffer.toList

  val set:Set[Int] = array.toSet
  val set1:Set[Int] = buffer.toSet

  val seq:Seq[Int] = array.toSeq
  val seq1:Seq[Int] = buffer.toSeq
}
