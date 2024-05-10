package org.example.collection

object TupleDemo extends App {
  //TODO 1.使用TupleN(...) apply(...)方法 (N的取 值范围：[1,22])创建元祖
  val tuple1:(Int,String,Int) = Tuple3(1,"土豪",20)
  val tuple2 = new Tuple3(2, "屌丝", 25)
  val tuple3: (Int, String, Int) = (3, "白富美", 18)
  println(tuple1._1)
  println(tuple1._2)
  println(tuple1._3)
  val iterator:Iterator[Any] = tuple2.productIterator
  iterator.foreach(println)
  println(tuple3.productElement(1))
  val kv1:(String, Int) = ("单身go", 1111)
  val kv2:(String, Int) = "单身go" -> 1111
  println(kv1.equals(kv2))
  val tuples:((Int,Int,Int),(Int,Int,Int))=((1,2,3),(4,5,6))
  println(tuples._1)
  println(tuples._2._3)
}
