package org.example.swith

object CaseTuple extends App {
  for (tuple <- Array((0,3),(2,0),(1,1),(1,0,2),(1,2,3,4))){
    val result = tuple match {
      case (0,_) => "0...."
      case (x, 0) => "" + x + "@0"
      case (x, y) => "" + x + "#" + y
      case (x,y,z) => "" + x + "#" + y + "#" + z
      case _ => "ddefult"
    }
    println(result)
  }
}
