package org.example.scala

object DoWhileLoop {
  def main(args: Array[String]): Unit = {
    var index = 0;
    do{

      print(index+1)
      index += 1
    }while(index<5)
  }
}
