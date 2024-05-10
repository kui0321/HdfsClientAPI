package org.example.scala

object FunctionRecursion {
  def main(args: Array[String]): Unit = {
    //TODO 1.递归函数
    /** 递归函数：在函数内部调用函数本身
     * 注意：1.scala中要求递归函数必须明确声明返回值类型
     * 2.递归函数一定要有跳出的出口
     * 3.传递的参数之间存在某种关系时才可以设计成递归函数
     * @param num
     * @return
     */
      def funcA(num:Int ):Int = {
        if (num == 1)
          num
        else
          num + funcA(num-1)
      }
    println(funcA(7))
  }
}
