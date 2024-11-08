package com.jason.test
// 需求：打印出九九乘法表
object TestWhile {
  def main(args: Array[String]): Unit = {
    for (i <- 1 to 9) {
      for (j <- 1 to i) {
        print(j + "*" + i + "=" + (i * j) + "\t")
      }
      println()
    }
  }

}
