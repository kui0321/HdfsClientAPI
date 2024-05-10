package org.example.collection

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object SeqTransformer extends App {
  val listbuffer: ListBuffer[Int] = ListBuffer(4,3,2,1)
  println(listbuffer)
  val list:List[Int] = listbuffer.toList
  val seq:Seq[Int] = listbuffer.toSeq
  println(list)
  println(seq)
  val ubffer:mutable.Buffer[Int] = list.toBuffer
  println(ubffer)
  val list1 = list.toSet
  println(list1)
  val list2 = list.toArray
  println(list2)
  val buffer = listbuffer.toSet
  val buffer2 = listbuffer.toArray
  println(buffer)
  println(buffer2)
  buffer2.foreach(le=>println(le))
  for (i <- 1 to buffer2.length){
    println(buffer2(i-1))
  }
}
