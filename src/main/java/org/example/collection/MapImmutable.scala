package org.example.collection

object MapImmutable extends App {
  val map1 =Map(1->"刘备",2->"guanyu",3->"张飞")
  println(map1)
  val map2:Map[Int,String] =Map[Int,String](6 -> "曹操", 7 -> "许褚", 8 -> "夏侯渊")
  println(map2)

  //val empty:Map[Nothing,Nothing] = Map[Nothing,Nothing]
  //val empty2:Map[Int,String] = Map[Int,String]

  val map3: Map[Int,String] = map1 + (4->"孙尚香")
  println(map3.mkString(","))
  val map4: Map[Int,String] = map3 - 4
  println(map4)

  val map5:Map[Int,String] = map1 ++ map2
  val map6:Map[Int,String] = map1 ++: map2
  println(map5)
  println(map6)

  val value1:Option[String] = map1.get(1)
  val value2:Option[String] = map2.get(7)

  println(value1 ++ value2)
  println(value1.isEmpty)
  println(value2.isEmpty)

  println(value1.get)
  println(value2.getOrElse("bucuizai"))

}
