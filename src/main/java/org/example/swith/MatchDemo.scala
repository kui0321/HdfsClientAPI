package org.example.swith

object MatchDemo extends App {
  val (_,name,_) = (1,"lixue",21)
  println(name)
  val map = Map((1, "张无忌"), (2, "赵敏"), (3, "周芷若"))
  for (kv  <- map){
    println(kv._1)
  }

  val list = List((("华为", "手机"), 100),
    (("华为", "手环"), 200),
    (("华为", "watch"), 300))

  val list1 = list.map(
    tup => {
      (tup._1._1, (tup._1._2, tup._2 * 3))
    }
  )
  println(list1)

  val list3 = list.map{
    case ((brank, kind),cnt) => (brank,(kind,cnt*3))
  }
  println(list3)
}
