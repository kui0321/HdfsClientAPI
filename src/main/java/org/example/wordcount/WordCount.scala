package org.example.wordcount

import scala.io.{BufferedSource, Source}

object WordCount {
  def main(args: Array[String]): Unit = {
    //TODO 1.读取文件
    val bufferedSource: BufferedSource = Source.fromFile("")

    //TODO 2.读取文件中所有的行
    val lines:Iterator[String] = bufferedSource.getLines()

    //TODO 3.将lines转换为List对象
    val linesList: List[String] = lines.toList
    println(linesList)
    //TODO 4.使用扁平化函数flatMap 将每行的内容拆分开成一个个的单词
    val wordsList: List[String] = linesList.flatMap(_.split(" "))
    //TODO 5.使用功能函数map将之映射为 每个单词->1
    val word2OneList: List[(String, Int)] = wordsList.map((_,1))
    //TODO 6.使用功能函数groupBy将相同的单词分为一组
    val wordGroupMap: Map[String,List[(String,Int)]] = word2OneList.groupBy(_._1)
    val wordCountMap:Map[String, Int] = wordGroupMap.map(
      keyval => {
        (keyval._1,keyval._2.size)
      }
    )
    println(wordCountMap)
    //TODO 7.如果wc.txt文件中出现多余的空格(修改 wc .txt文件最后一行"hello joy" -> "hello   joy"

  }
}
