package org.example.scala

object ConstructMethod {
  def main(args: Array[String]): Unit = {
    val student = new Student()
    val student1 = new Student("nihao", 20)
    println(student, student1)
  }
}
class Student{
  var name:String = _
  var age:Int = _

  def this(name:String){
    this()
    this.name = name
    println("this(name)")
  }

  def this(age:Int){
    this()
    this.age = age
  }

  def this(name:String,age:Int){
    this(name)
    this.age = age
  }

  def Student() ={
    println("student()调用")
  }

  override def toString = s"Student($name, $age)"
}



