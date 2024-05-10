package org.example.scala

object Accompanying {
  def main(args: Array[String]): Unit = {
    //调用的伴生对象的apply方法
    val person2:Person = Person()
    //调用类的构造方法
    val person3 = new Person()
    val person4 = Person  //调用伴生对象本体
    println(person4)
    for (ele <- Range(1,5,2)){
      println(ele+",")
    }
  }


  class Person(){
  }
  object Person{
    def apply():Person={
      println("apply()")
      new Person
    }
  }
}
