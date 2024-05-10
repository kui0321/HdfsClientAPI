package org.example.scala

object FunctionToTypeValue {
  def main(args: Array[String]): Unit = {
    def funcA():String={
      println("funcA被执行了。。。")
      "GTJin"
    }
    //使用 "函数名 + 空格 + _",将函数作为对象赋值给x，在赋值的过程中不会函数体中的代码
    val x = funcA _
    //写下 x 后提示框显示"x ()=>String",表示x是一个无参返回值类型为String的函数
    def funcB(name: String): String = {
      "funcB....name=" + name
    }
    //函数类型还有另外一种表示方式：(参数类型)=>返回值类型，多个参数之间使用逗号隔开
    val f2:(String)=>String  = funcB _
    //如果函数只有一个参数小括号可以省略
    val f2_1:String=>String  = funcB _

    def funcC(name: String, age: Int): String = {
      s"name=${name},age=${age}"
    }

    //两个或两个以上参数时，小括号不能省略
    val f3:(String,Int)=>String = funcC _
    /** val x = funcA _为什么在函数名后 使用下滑线让
     *  函数作为对象使用，因为代码中没有明确的变量
     * 类型，所以需要通过取值类型进行推断。
     * 如果变量声明时的类型为函数类型，那么可以不适用下
     *  划线让函数作为对象
     *
     */
    val f4: (String) => String = funcB
    val f5: (String, Int) => String = funcC
    println(f3("niao",23))
  }
}
