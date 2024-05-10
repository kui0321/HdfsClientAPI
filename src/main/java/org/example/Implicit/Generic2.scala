package org.example.Implicit

object Generic2 {
  class Animal{}
  class Person extends Animal{}
  class Man extends Person()
  class Car[T]{}

  class Truck[+T]{}
  class Bus[-T]{}

  def main(args: Array[String]): Unit = {
    val car:Car[Person] = new Car[Person]
    val truck1:Truck[Person] = new Truck[Person]

    val bus1:Bus[Person] = new Bus[Person]
    val bus2:Bus[Person] = new Bus[Animal]

    println(car)
    println(truck1)
    println(bus1)
    println(bus2)
  }
}
