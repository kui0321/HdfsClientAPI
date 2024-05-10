package org.example.scala

object ForLoop {
  def main(args: Array[String]): Unit = {
    for (i <- 1 to (10)) {
      print(i+" ")
    }
    println()
    for (j <- 0 until(10)){
      print(j+" ")
    }
    println()
    for (a <- 1 to(10,2)){
      print(a+" ")
    }
    println()
    for (b <- 0 until(20,2)){
      print(b + " ")
    }
    println()
    for (c <- Range(1,10,3)){
      print(c + " ")
    }

    println()

    for (i <- Range(1,10) if i!=3){
      print(i+" ")
    }
    println()
    for (i <- Range(1,20) if i !=3| i!=4){
      print(i+" ")
    }
    println()
    for (i <- Range(1,10)){
      for (j <- Range(1,i+1)){
        print(j+"*"+i+"="+i*j +"\t")
      }
      println()
    }
    println()
    for (i <- 1 to 10; j <- 1 to  10){
      print(i,j)
    }

    println()
    for (i<- Range(1,5); j <- Range(1,4)){
      println(i,j)
    }
    println()
    for (i <- 1 until  10; j<- 1 until(i+1)){
      print(j+"*"+i+"="+i*j+"\t")
    }

    println()
    var reslur = for (i <- 1 to 10) {
      i*i
    }
    print(reslur)
    println();
    var result = for (i <- 1 to 10) yield{
      i * i
    }
    print(result)
  }
}
