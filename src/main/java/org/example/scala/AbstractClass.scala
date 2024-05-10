package org.example.scala

object AbstractClass {
  def main(args: Array[String]): Unit = {
    //TODO 3.抽象类不能实例化对象
    //val animal = new Animal()

  }
  //TODO 2.抽象类的定义：就是包含抽象方法的类。需要在类声明时使用abstract关键字修饰
  abstract class Animal{
    //TODO 1.抽象方法定义：就是不完整的方法，只有声明没有实现
    //scala中不完整的方法就是抽象方法，故此不需要使用abstract关键字修饰
    def move():Unit
    def eat():Unit={
      println("jijiwaiwai")
    }
    //TODO 4.继承抽象类后，要么重写抽象类中的全部的抽象方法，要么将该类也定义为抽象类（可以全部不重写或部分重写）
    class Dog extends Animal{
      //TODO 5.子类中可以空实现，但不能不实现
      //TODO 7.重写父类中的抽象方法，override关键字可用可不用
      override def move(): Unit = {
      }
      //TODO 8.重写父类中的非抽象方法，override关键字必须使用
      //开发时推荐只要是重写父类的方法，不论它是否为抽象方法，都是用override。
      override def eat(): Unit = super.eat()
    }
  }
}
