package org.example.collection

import scala.collection.mutable.ListBuffer

object CollectionMetMap1 {
  def main(args: Array[String]): Unit = {
    val buffer:ListBuffer[ListBuffer[Int]] = ListBuffer(ListBuffer(1,2),ListBuffer(3,4))
    println(buffer.length)
    print(buffer)
    println(buffer.flatten)

    val buffer1 = ListBuffer(ListBuffer(
      ListBuffer(1,2),ListBuffer(3,4)
    ),ListBuffer(
      ListBuffer(5,6),ListBuffer(7,8)
    ))

    println(buffer1.flatten)
    println(buffer1.flatten.flatten)

    val listBuffer: ListBuffer[String] = ListBuffer(
      "Hello Tom","Hello Peter"
    )
    val words: ListBuffer[String] = listBuffer.flatMap(_.split(" "))
    println(listBuffer.flatten)

    println(words)
  }
}
