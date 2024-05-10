package org.example.swith

object CaseList extends App {
  for (list <- Array(List(0),List(1,0),List(0,1),List(0,0,0),List(6))){
    val result = list match {
      case List(0) => "0"
      case List(x,y) => x + "@" + y
      case List(0,_*) => "0..."
      case _ => "other list"
    }
    println(result)
  }

  val list:List[Int] = List(1,2,3,4,5,6)
  list match {
    case one :: two :: rest => println(one + "-" + two + "-" + rest)
    case _ => println("other else")
  }
}
