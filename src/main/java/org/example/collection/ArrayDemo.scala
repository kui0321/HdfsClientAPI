package org.example.collection

object ArrayDemo extends App {
  val arr1 = new Array[Int](3)
  val arr2:Array[Int] = Array(1,2,3)

  println(arr1+"\n"+arr2)
  println(arr1.length)
  println(arr1.mkString(","))
  println(arr2.mkString(","))

  arr1(0) = 8
  arr1.update(1,9)
  println(arr1.mkString(","))

  val arr3:Array[Int] = arr1 :+ 5
  println(arr3.mkString(","))

  val arr4:Array[Int] = 5 +: arr1
  println(arr4)

  println(arr4.mkString(","))

  val arr5:Array[Int] = arr1 ++ arr2
  println(arr5.mkString(","))

  val arr6:Array[Int] = arr1 ++: arr2
  println(arr6.mkString(","))

  arr6.foreach(print)
  for (ele <- arr1) print(ele + "\t")
  println()

  //创建一个3*2的二维数组
  val matrix:Array[Array[Int]] = Array.ofDim[Int](3,2)
  matrix.foreach(arrele=>arrele.foreach(println))

  val arr7:Array[Int] = Array.concat(arr1,arr2)
  println(arr7.mkString(","))

  val arrRange1:Array[Int] = Array.range(0,3)
  println(arrRange1.mkString(","))

  val arrFill:Array[Int] = Array.fill[Int](5)(6)
  println(arrFill.mkString(","))
}
