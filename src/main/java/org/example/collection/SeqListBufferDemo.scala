package org.example.collection

import scala.collection.mutable.ListBuffer

object SeqListBufferDemo extends App {
  val names = ListBuffer[String]()
  names.append("乔峰")
  names.append("段誉")
  names.append("xuzu")
  names.update(0,"azhu")
  names.foreach(println)
  println(names)
  val names1:ListBuffer[String] = names.updated(1,"唐山")
  println(names1)
  names.remove(0)
  println(names)
}
