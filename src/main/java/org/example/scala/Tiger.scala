package org.example.scala

class Tiger {

}
object ClassDemo{
  //类的不同位置
  def main(args: Array[String]): Unit = {
    class InnerClass2{

    }
    //实例化对象
    val tiger = new Tiger()
  }
  //类的不同位置
  class InnerClass{

  }
}
//scala中一个源文件中可以声明多个公共类
class ClassB{
  class classBinner{}
}
