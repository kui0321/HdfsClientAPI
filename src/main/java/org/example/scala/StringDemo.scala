package org.example.scala

object StringDemo {
  def main(args: Array[String]): Unit = {
    var name = "hello";
    val nameString2:String = String.format("%s word ", name);
    println(nameString2)

    var line:String = s"hello word ${name} nihao"
    println(line)
  }
}
