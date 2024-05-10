package org.example.scala

object FunctionXArgs {
  def main(args: Array[String]): Unit = {
    def funcA(element: Int*): Int ={
      //遍历求和
      var sum = 0
      // 遍历求和
      for (esl <- element){
        sum += esl
      }
      sum
    }
    println(funcA(1,2,3,4,7,5,6))

    def funcB(ename: String, score: Int*): String = {
      var sum = 0
      for (ele <- ename) {
        sum += ele
      }
      s"姓名：${ename},总成绩为:${sum}"
    }

    println(funcB("zhangshan",23,343,534,2,54))
  }
}
