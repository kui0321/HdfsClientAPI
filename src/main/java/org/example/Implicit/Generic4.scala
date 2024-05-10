package org.example.Implicit

object Generic4 {
  class Animal {}
  class Person extends Animal{}
  class Man extends Person {}

  def main(args: Array[String]): Unit = {
    val list: List[Person] = List(
      new Person(),new Person(), new Person()
    )
    val animal: Animal = list.reduce[Animal](
      (p1,p2) => {
        p1
      }
    )
    list.fold[Animal](new Animal)(
      (x,y) => x
    )
    list.foldLeft[Man](new Man)((x,y) => x)
  }
}
