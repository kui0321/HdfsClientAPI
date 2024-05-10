package org.example.scala

object FunctionToTypeArg1 {
  //函数作为参数
  def main(args: Array[String]): Unit = {
    //TODO 1 无参无返回值
    /** public void method(Student stu){}
     * public void method(参数类型 参数名称){}
     * Scala同理，让函数作为参数
     * fun:()=>Unit 表示参数是一个函数对象，这个函数是一个无参无返回值的函数
     * ()表示是一个无参的函数
     * Unit表示是一个无返回值的函数
     */
    def funcA(fun:()=>Unit):Unit={
      //调用传递过来的函数
      fun()
    }
    def funObject():Unit={
      println("funcObjA被调用")
    }
    funcA(funObject _)
    // TODO 2 有参数以后返回值
    /** fun:(Int,Int)=>Int) 表示funcB的参数为一个函数
     * (Int,Int) 这个函数是一个有两个参数的函数，这两个参数都是Int类型
     * Int 表示这个函数的返回值是一个Int类型
     * @param fun
     */
    def funcB(fun:(Int,Int)=>Int): Unit ={
      //该设计缺陷是 fun(2,1) 写死的参数值
      val resule  = fun(2,1)
      println(resule)
    }
    def funObj(x:Int,y:Int): Int ={
      x+y
    }
    funcB(funObj)

    def funcObj1(x:Int,y:Int): Int ={
      x-y
    }
    funcB(funcObj1)
    //TODO 3.省略 作为参数的函数
    //省略关键字、函数名、返回值类型;在将 ": =" 改为 "=>"

    funcB((x:Int,y:Int)=>{
      x/y
    })
    //继续简化： 函数体只有一行，函数体外的{}可以省略
    funcB((x: Int, y: Int) => x / y)
    //参数的类型如果能够推断出来，参数的类型可以省略
    funcB((x,y)=>x/y)
    //如果参数只有一个的话，参数列表的小括号可以省略
    //funcB(x,y=> x/y)错误的 参数>=2话，参数列表的小括号不可以省略
    //如果参数在使用时，按照声明的顺序只使用一次时，那么可以使用下划线代替
    funcB((_+_)) //继续省略
    funcB(_*_)
  }
}
