package org.example.Implicit

object Generic1 {
  class Animal{}
  class Person extends Animal{}
  class Man extends Person{}
  class Car[T]{}

  def main(args: Array[String]): Unit = {
    val car1:Car[Man] = new Car[Man]
    println(car1)
  }
}
