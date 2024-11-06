package com.jason.test
// Unit 类型用来标识过程，也就是没有明确返回值的函数。
// Null 类只有一个实例对象，Null 类似于 Java 中的 null 引用。

object TestDataType {
  def main(args: Array[String]): Unit = {
    var cat = new Cat()
    cat = null  // right
    println(cat)
    //var n1:Int = null // wrong
    //print(n1)

    /*
    * Nothing，可以作为没有正常返回值的方法的返回类型，非常直观的告诉你这个方
      法不会正常返回，而且由于 Nothing 是其他任意类型的子类，他还能跟要求返回值的方法兼容。
    * */
    def test() : Nothing={
      throw new Exception()
    }
    test
  }

}
class Cat{}
