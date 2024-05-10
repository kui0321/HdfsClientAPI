package org.example.collection

import scala.collection.mutable

object MapMutable extends App {
  val map1 = mutable.Map(1 -> "刘备", 2 -> "关羽", 3 -> "张飞")
  val map2 = mutable.Map(6 -> "曹操", 7 -> "许褚", 8 -> "夏侯渊")

  map1.put(4,"赵云")
  println(map1)
  val map3:mutable.Map[Int,String] = map1 + (5->"李靖")
  println(map3,map1 eq(map3))
  val map4:mutable.Map[Int,String] = map1 += (6->"哪吒")
  println(map4,map1 eq(map4))
  map1.update(1,"刘玄德")
  println(map1)
  map1(1)= "刘备"
  println(map1)
  map1.remove(1)
  println(map1)
  val map5:mutable.Map[Int,String] = map1 - 2
  println(map5+"\r\n"+map1)
  val map6:mutable.Map[Int,String] = map2 -= 3
  println(map6 +"\r\n"+map1)
  val map7 = map1 ++ map2
  println(map7)
  val set1 = map1.toSet
  println(set1)
  val list1 = map1.toList
  println(list1)
  val array1 = map1.toArray
  println(array1.mkString(","))

  println(map1.get(2))
  println(map1.getOrElse(1,"长老"))

  val keys:Iterable[Int] = map1.keys
  println(keys)

  val keyset:collection.Set[Int] = map1.keySet
  println(keyset)

  val keysIterator:Iterator[Int] = map1.keysIterator
  println(keysIterator)

  val values: Iterable[String] = map1.values
  println(values)

  val valuesIterator: Iterator[String] =map1.valuesIterator
  println(valuesIterator)


}
