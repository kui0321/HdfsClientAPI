package org.example.scala

object TraitDemo3 {

  trait ProductDao extends Exception{
    def insert():Unit
    def update():Unit
  }
  class ProductDaoImpl extends ProductDao{
    override def insert(): Unit = ???

    override def update(): Unit = ???
  }

  def main(args: Array[String]): Unit = {
    val productDao = new ProductDaoImpl
  }
}
