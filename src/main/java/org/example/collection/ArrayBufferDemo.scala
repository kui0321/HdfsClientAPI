package org.example.collection

import scala.collection.mutable.ArrayBuffer

object ArrayBufferDemo extends App {
  val arrbuf1 = ArrayBuffer('a', 'b', 'c', 'd')
  val arrbuf2 = ArrayBuffer('u', 'v', 'w', 'x')

  val arrbuf3:ArrayBuffer[Char] = arrbuf1 += 'e'

  println(arrbuf1 eq arrbuf3)
  arrbuf1.append('f','g')
  println(arrbuf1)

  arrbuf1(0)
  arrbuf1.updated(0,'A')

  println(arrbuf1.mkString(","))
  arrbuf1.remove(5,2)

  println(arrbuf1)
  println(arrbuf1.mkString(","))

  val arrbuf4:ArrayBuffer[Char] = arrbuf1 ++ arrbuf2
  println(arrbuf4.mkString(","))

  val arrbuf5:ArrayBuffer[Char] = arrbuf1 ++= arrbuf2
  println(arrbuf5.mkString(","))

  println(arrbuf1 eq arrbuf4)
  println(arrbuf1 eq arrbuf5)

}
