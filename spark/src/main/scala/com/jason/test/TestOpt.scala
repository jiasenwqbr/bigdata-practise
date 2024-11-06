package com.jason.test
/*
* 在 Scala 中其实是没有运算符的，所有运算符都是方法。
  1）当调用对象的方法时，点.可以省略
  2）如果函数参数只有一个，或者没有参数，()可以省略
* */
object TestOpt {
  def main(args: Array[String]): Unit = {
    // 标准的加法运算
    val i = 1.+(1)
    // （1）当调用对象的方法时，.可以省略
    val j = 1.+ (1)
    // （2）如果函数参数只有一个，或者没有参数，()可以省略
    val k = 1+1

    println(1.toString())
    println(1 toString())
    println(1 toString)

  }

}
