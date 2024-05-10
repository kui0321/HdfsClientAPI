package org.example.scala

object EnumDemo {
  def main(args: Array[String]): Unit = {
    println(Kpi.NEW_INSTALL_USERValue)
    println(Kpi.BROWSER_NEW_INSTALL_USER.id)
    println(Kpi.BROWSER_NEW_INSTALL_USER.toString)
  }

  object Kpi extends Enumeration{
    val NEW_INSTALL_USERValue=(1, "new_install_user")
    val BROWSER_NEW_INSTALL_USER = Value(2, "browser_new_install_user")
  }
}
