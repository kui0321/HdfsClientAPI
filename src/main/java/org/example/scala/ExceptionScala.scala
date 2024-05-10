package org.example.scala

object ExceptionScala {
  def main(args: Array[String]): Unit = {
    try{
      var result = 10 / 0;
    }catch {
      //先写的异常能够匹配上就不走后写的
      case arithmeticException:
        ArithmeticException =>
        //省略算术异常的处理
        println("发生算术异常"+arithmeticException.getMessage)
      case exception: Exception =>
        //对异常进行处理...
        println("发生了Exception异常")
    }finally {
      println("finally")
    }
  }
}
