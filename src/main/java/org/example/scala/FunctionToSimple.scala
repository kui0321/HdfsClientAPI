package org.example.scala

object FunctionToSimple {
  def main(args: Array[String]): Unit = {
    //1.省略return关键字
    def func1():String={
      return "hello"
    }
    def func2():String={
      "word"
    }
    println(func1(),func2())
    //2.省略花括号
    def funcA():String = "hello word"
    //3.省略返回值类型
    def funcB() = "hello word"
    //4.省略参数列表
    def funcC = "ni hao"
    println(funcA(),funcB(),funcC)
    //5.省略": 返回值类型 ="
    //如果函数体返回值类型明确为Unit, 那么函数体中即使有return关键字也不起作用
    //funcD1(),funcD2() 是等价的
    def funcD1():Unit={
      "genhel"
    }
    def funcD2(){
      "genhel"
    }
    print(funcD1(),funcD2())
    //如果函数体中有明确的return语句，那么返回值类型不能省略
    def funcD3():String = {
      return "nihao"
    }
    def funcD4:Unit={
      "nihao"
    }
    println(funcD3())
    println(funcD4)
    ()=>{
      println("省略名称和关键字GTJin")
    }
  }
}
