package org.example.swith

object PartialFunction1 extends App{
  val list = List(1,2,3,4)
  val list1: List[AnyVal] = list.map(
    ele =>{
      if (ele % 2 ==0){
        ele * 2
      }
    }
  )
  println(list1)

  val list2:List[AnyVal] = list.map(
    ele => {
      if (ele % 2 == 0) {
        ele * 2
      }else{
        ele
      }
    }
  )
  println(list2)
}
