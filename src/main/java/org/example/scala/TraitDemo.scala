package org.example.scala

import org.example.scala.AbstractField.Animal

object TraitDemo {

  trait Eatable{
    def eat():Unit
  }

  class Dog extends Eatable{
    override def eat(): Unit = {
      println("吭哧吭哧的eat...")
    }
  }

  trait Sleepable {
    def sleep:Unit
  }

  class Cat extends Eatable with Sleepable{
    override def eat(): Unit = println("吭哧吭哧的eat...1")

    override def sleep: Unit = println("吭哧吭哧的eat...2")
  }

  class Tiger extends Animal with Eatable{
    override var name: String = _

    override def eat(): Unit = {
    }
  }

  trait Drinkable{
    def drink():Unit = {
      println("drink....")
    }
  }

  def main(args: Array[String]): Unit = {
    val dog = new Dog() with Drinkable
    dog.eat()
    dog.drink()
  }
}
