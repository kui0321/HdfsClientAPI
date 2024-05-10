package org.example.collection

object SeqListDemo extends App {
  val seq = Seq(1,2,3,4)
  println(seq)
  val list = List(2,3,4,5)
  println(list)
  //TODO 4.向集合的尾部添加元素
  val insertList: List[Int] = list :+ 5
  println(insertList)
  insertList.foreach(ele=>println(ele))
  println(list eq insertList)
  //TODO 6.向集合的头部添加元素
  val insertList1:List[Int] = list.:+(6)
  println(insertList1)
  //list.+:(5)简化后list +: 5 编译不通过，只能换种写法5+:list
  val insertList2:List[Int] = 6 +: list
  println(insertList2)
  //TODO 7.Nil表示的是一个空的List集合
  println(Nil)//List()
  val insertList3:List[Int]=1::2::3::Nil
  println(insertList3)
  val list2 = List(5, 6, 7, 8)
  val insertList4:List[Any] = 1::2::3::list2 :: Nil
  println(insertList4)
  val intsList6 = List(1, 5, 3, 6, 5)
  println("intsList6:"+intsList6.take(2))
  val insertList7:List[Int] = intsList6.drop(2)
  println(intsList6)
  println(insertList7)
}
