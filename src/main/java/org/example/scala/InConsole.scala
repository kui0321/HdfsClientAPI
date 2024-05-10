package org.example.scala

import scala.io.StdIn

object InConsole {
  def main(args: Array[String]): Unit = {
    ////获取控制台输入的字符
    val char1: Char = StdIn.readChar()
    println(char1)
    //获取控制台输入的Int
    val demo = StdIn.readInt()
    println(demo)
    //获取控制输入的Boolean
    val booleandemo = StdIn.readBoolean()
    println(booleandemo)
  }
}
