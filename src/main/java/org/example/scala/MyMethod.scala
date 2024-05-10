package org.example.scala

class MyMethod {
  var id:Int = 1
  override def equals(obj:Any):Boolean={
    //类型是否相同
    if(obj.isInstanceOf[MyMethod]){
      //类型转换
      val other = obj.asInstanceOf[MyMethod]
      //在比较id是否相同
      this.id == other.id
    }else{
      false
    }
  }
}

object ClassMethod{
  def main(args: Array[String]): Unit = {
    val myMethod1 = new MyMethod
    val myMethod2 = new MyMethod
    //未覆写MyMethod类的equals方法时，都是false
    //覆写MyMethod类的equals方法后，都是True
    println(myMethod1 == myMethod2)
    println(myMethod1.equals(myMethod2))

    Predef.println("scala")
    println("scala")
  }
}
