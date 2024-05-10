package org.example.swith

object CaseObject {
  def main(args: Array[String]): Unit = {
    val stu = getStudent()
    stu match {
      case Student(2, "关兴") => println("fuanyu")
      case _ => println("who are you?")
    }
  }
  class Student {
    var id: Int = _
    var name: String = _
  }
  object Student{
    def apply(id:Int, name:String):Student = {
      val stu = new Student()
      stu.id = id
      stu.name = name
      stu
    }
    def unapply(stu: Student): Option[(Int,String)] = {
      Option(stu.id,stu.name)
    }
  }
  def getStudent(): Student = {
    Student(2, "关兴")
  }
}
