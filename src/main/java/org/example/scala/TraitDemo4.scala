package org.example.scala

object TraitDemo4 {
  trait XDao{
    println("trait X...")
  }
  class Father{
    println("class father")
  }
  class Son extends Father with XDao{
    println("son....")
  }
  trait YDao{
    println("trait y...")
  }
  class Son2 extends Father with XDao with YDao{
    println("son2....")
  }
  def main(args: Array[String]): Unit = {
    //val son = new Son
    new Son2
  }
}
