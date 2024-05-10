package org.example.scala

object TraitDemo2 {
  class ProductVO{}

  trait ProductDao{
    def insert(pv:ProductVO):Int
    def update(pv:ProductVO):Boolean
  }
  class ProductDaoImpl extends ProductDao{
    override def insert(pv: ProductVO): Int = {println("insertProduct...")
      1}
    override def update(pv: ProductVO): Boolean = {
      println("updateProduct...")
      true
    }
  }

  def main(args: Array[String]): Unit = {
    val proDao:ProductDao = new ProductDaoImpl
  }

}
