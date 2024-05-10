package org.example.swith

object CaseBase extends App {
  var x:Int = 4
  var y:Int = 2
  var operator:Char = '%'
  var result = operator match {
    case '+' => x + y
    case '-' => x - y
    case '*' => x * y
    case '/' => x / y
    case '%' => x % y
  }
  println( result)
}
