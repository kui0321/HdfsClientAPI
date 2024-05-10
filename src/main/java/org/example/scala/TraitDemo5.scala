package org.example.scala

object TraitDemo5 {
    trait Operator{
      def optdata=println("操作数据")
    }
    trait DataBase extends Operator{
      override def optdata: Unit = {
        println("向数据库中")
        super[Operator].optdata
      }
    }
  class Hbase extends Operator{}

  def main(args: Array[String]): Unit = {
    new Hbase().optdata
    new DataBase {
      optdata
    }
  }
}
