package org.example.Implicit

object ImplicitTransform3 {
  def main(args: Array[String]): Unit = {
    val productDao = new ProductDao()
    productDao.insertProduct()
    productDao.updateProduct()
  }

  implicit class ProductDaoExt(productDao: ProductDao){
    def updateProduct(): Unit = {
      println("update producet")
    }
  }

  class ProductDao{
    def insertProduct(): Unit = {
      println("insert producet")
    }
  }
}
