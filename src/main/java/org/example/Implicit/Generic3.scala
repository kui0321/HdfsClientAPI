package org.example.Implicit

object Generic3 {
  class Animal {}
  class Person extends Animal {}
  class Man extends Person {}
  class Car[T] {}
  class Producer[T]{
    def producer(car: Car[_ >: T]):Unit = {
      println(car)
    }
  }
  class Consumer[T]{
    def consumer(car: Car[_ <: T]):Unit = {
      println(car)
    }
  }
  def main(args: Array[String]): Unit = {
    val producer = new Producer[Person]
    producer.producer(new Car[Animal])
    producer.producer(new Car[Person])

    val consumer = new Consumer[Person]
    consumer.consumer(new Car[Man])
    consumer.consumer(new Car[Person])
  }
}
