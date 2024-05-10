package org.example.swith

object PartialFunction2 extends App {
    val list:List[Any] = List(1,2,"gtion",3,4)
    val list1:List[Any] = list.filter(_.isInstanceOf[Int])

  val list2:List[Int] = list1.map(_.asInstanceOf[Int] + 1)
  println(list2)

  list.map{
    case num:Int => num + 1
    case other => other
  }.filter(_.isInstanceOf[Int]).foreach(println)
}
