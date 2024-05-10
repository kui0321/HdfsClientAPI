package org.example.scala

/**
 * package 定义声明所在的包
 * Object 声明一个对象,在编译时会被编译成两个字节码文件：chapter01.class和chapter01$.class
 */
object chapter01 {
  /**
   * main 函数时程序的入口
   * def 声明函数或方法的关键字
   * Unit 没有返回值
   * @param args String类型的数组
   */
  def main(args: Array[String]): Unit = {
    println("hello word")
  }
}
