package org.example.scala

import scala.util.control.Breaks.{break, breakable}

object LoopInterrupt {
  def main(args: Array[String]): Unit = {
    breakable {
      for (i <- 1 to 10) {
        if (i == 3) {
          break
        }
        print(i + "\t")
      }
    }
    println()

      for (i <- 1 to 10) {
        breakable {
          if (i == 4) {
            break()
          }
          print(i + "\t")
        }
      }
    }
}
