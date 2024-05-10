package org.example.scala
import java.lang.reflect.Field

object ReflectString {
  def main(args: Array[String]): Unit = {
    var str = "x z"
    val stringClass:Class[String] = classOf[String]
    val valueField:Field = stringClass.getDeclaredField("value")
    valueField.setAccessible(true)
    val obj:AnyRef = valueField.get(str)
    val chars:Array[Char]= obj.asInstanceOf[Array[Char]]
    println("修改前"+str)
    chars(1) = 'H'
    println("修改后"+str)
  }
}
