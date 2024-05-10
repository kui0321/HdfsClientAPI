package org.example.scala

object FunctionToFunResult {
  def main(args: Array[String]): Unit = {
    //TODO 1.对比Java代码
    /** public Student getStudent(){
     * return new Student();
     * }
     * 返回的是一个对象，而在Scala中函数也是一个对象，
     * 所以也可以使用一个函数作为另外一个函数的返回值
     */
    def resultFun():Unit={
      println("resultFUn")
    }
    def funcA():()=>Unit={
      resultFun _ //返回的是函数对象
    }
    val f1 = funcA _
    //f2就是resultFun
    val f2 = f1()
    //调用f2
    f2()
    //简化以上调用 以后的代码中经常遇到
    funcA()()
  }
}
