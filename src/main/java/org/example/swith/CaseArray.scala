package org.example.swith

object CaseArray  extends  App {
  val arrays =Array(
    Array(0),
    Array(0,1),
    Array(1,0),
    Array("gtjin","peter"),
    Array(0,1,0),
    Array(2,1,0),
    Array(3,1,0,1)
  )
  for (arr <- arrays){
    val result = arr match {
      case Array(0) => "0"
      case Array(x, y) => x + "@" + y
      case Array(0,_*) =>  "以0开头的数据"
      case _ => "others array"
    }
    println(result)
  }
}
