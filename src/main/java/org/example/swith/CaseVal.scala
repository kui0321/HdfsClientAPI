package org.example.swith

object CaseVal extends App {
  def matchval(arg:Any) = arg match {
    case 8 => "Int 8"
    case "6" => "String 6"
    case true => "Boolean true"
    case '-' => "Char -"
    case "+" => "String +"
    case _ => "other val"
  }
  println(matchval(8))
  println(matchval(6))
  println(matchval("+"))
}
