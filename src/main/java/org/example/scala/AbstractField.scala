package org.example.scala

import scala.beans.BeanProperty

object AbstractField {
  def main(args: Array[String]): Unit = {
    val cat = new Cat()
    println(cat.age)
    cat.changeAge()
    println(cat.age)
  }
  //包含抽象属性的类必须定义为抽象类
  abstract class Animal{
    //抽象属性的定义，只有声明没有显示初始化的属性
    var name:String
    val age:Int = 1
    def changeAge():Unit={
      println("inner change:"+age)
    }
  }
  class Cat extends Animal{
    override var name: String = _
    override val age: Int = 2
  }
}
