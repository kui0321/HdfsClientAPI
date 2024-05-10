package org.example.collection

import scala.collection.mutable.ArrayBuffer

object CollectionMetMap {
  def main(args: Array[String]): Unit = {
    val arrBuf:ArrayBuffer[Int] = ArrayBuffer(1,2,3,4,5)


    //arrBuf.foreach(ele=>println(ele*2))

    val buff:ArrayBuffer[Int] = multi2(arrBuf)
    println(buff)
    val buffs:ArrayBuffer[Int] = multi(arrBuf, 4)
    println(buffs)

    def mapFun(ele:Int):Int={
      ele * 2
    }

    println(arrBuf.map(mapFun))
    println(arrBuf.map(_*2))
    println(arrBuf.map(_+2))


  }
  def multi2(arrBuf:ArrayBuffer[Int]):ArrayBuffer[Int]={
      val result:ArrayBuffer[Int] = ArrayBuffer[Int]()
      arrBuf.foreach(ele=>{
        result.append(ele*2)
      })
    result
  }

  def multi(arrayBuffer: ArrayBuffer[Int],sum:Int):ArrayBuffer[Int]={
    val result:ArrayBuffer[Int] = ArrayBuffer[Int]()
    arrayBuffer.foreach(ele=>{
      result.append(ele*sum)
    })
    result
  }
}
