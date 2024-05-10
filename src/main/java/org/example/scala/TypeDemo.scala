package org.example.scala

object TypeDemo extends App {
 type KIntVStrMap = java.util.HashMap[Int, String]
  private val map = new KIntVStrMap()
  map.put(1,"java")
  map.put(2,"scala")
}
