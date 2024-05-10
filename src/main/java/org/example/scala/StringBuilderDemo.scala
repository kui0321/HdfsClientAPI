package org.example.scala

import scala.collection.mutable

object StringBuilderDemo {
  def main(args: Array[String]): Unit = {
    val strBui = new StringBuilder()
    strBui.append("abc");
    strBui.+('b')
    strBui.+=('c')
    strBui+='g'
    strBui.++=("fjkdfl")
    strBui.++=("hello")
    strBui.append(3.1f)
    println(strBui)
  }
}
