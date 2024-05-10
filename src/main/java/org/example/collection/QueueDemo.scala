package org.example.collection
import scala.collection.mutable

object QueueDemo {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建一个队列
    val que = mutable.Queue[String]()
    // 添加一个元素
    que.enqueue("a")
    que.enqueue("b","c")
    //TODO 4.遍历输出
    que.foreach(els=>println(els))
    //TODO 5. 添加元素+= ,修改原队列
    val que1:mutable.Queue[String] = que += "d"
    println(que1.length)
    println(que eq que1)
    println(que.dequeue())
    println(que.length)
    //获取队列中的元素
    println(que.dequeue())
    println(que.dequeue())
  }
}
