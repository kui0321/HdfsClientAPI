package org.example.scala

class Privilege {
  private var field1:String = _
  private[scala] var field2:String = _
  protected var field3:String = _
  var field4:String = _

  def printerInfo():Unit={
    println(field1)
    println(field2)
    println(field3)
    println(field4)
  }
}

class PrivilegeSub extends Privilege{
  def showInfo():Unit={
    println(field2)
    println(field3)
    println(field4)
  }
}

class Privilege2{
  def printinInfo():Unit={
    val p = new Privilege()
    println(p.field2)
    println(p.field4)
  }
}

object AccessPrivilege {
  def main(args: Array[String]): Unit = {
    val privilege = new Privilege
    privilege.printerInfo()
    val privilegeSub = new PrivilegeSub
    privilegeSub.showInfo()
    val privilege2 = new Privilege2
    privilege2.printinInfo()
  }
}
