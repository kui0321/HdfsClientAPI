package org.example.scala
//TODO 1.构造方法私有化:在主构造方法的参数列表前加private关键字实现
class UserScala private(){
  //TODO 2.非静态的属性和方法写在伴着类中
  var age:Int = _
}
//TODO 3.scala采用特殊的方式来代替静态语法：
//TODO 4.定义：这个伴生对象等同于随着对应类被加载时所产生，所以该对象被称为伴生对象；对应的类被称为伴生类。
object UserScala{//TODO 5.2伴生对象被编译为
  //TODO 6.静态的属性和方法写在伴生对象中
  var name:String = _
  def getInstance():UserScala = {
    new UserScala()
  }
}
