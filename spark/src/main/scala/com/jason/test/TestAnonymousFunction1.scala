package com.jason.test

object TestAnonymousFunction1 {
  def main(args: Array[String]): Unit = {
      def calculator(a: Int, b: Int, op: (Int, Int) => Int): Int = {
        op(a, b)
      }
    // （1）标准版
    println(calculator(2, 3, (x: Int, y: Int) => {x + y}))

    // （2）如果只有一行，则大括号也可以省略
    println(calculator(2, 3, (x: Int, y: Int) => x + y))

    // (3) 参数的类型可以省略
    println(calculator(2,3,(x,y) => x+y))

    // (4) 如果参数只出现一次，则参数省略后面参数可以用_代替
    println(calculator(2,3,_ + _))

  }

}
