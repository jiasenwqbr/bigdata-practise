package com.jason.test

object TestFor {
  def main(args: Array[String]): Unit = {
    // 范围数据循环（To）
    for (i <- 1 to 5){
      println("----------------"+i)
    }
    // 范围数据循环（Until）
    for (i <- 1 until 5){
      println("****************"+i)
    }
    // 循环守卫
    for (i <- 1 to 3 if i!=2){
      println("-----------------"+i)
    }

    // 循环步长
    for (i <- 1 to 10 by 2){
      println("******************"+i)
    }

    // 嵌套循环
    for (i <- 1 to 3 ;j=4-i){
      println("i="+i + "j="+j)
    }
    /*
    * 上面的代码等价
      for (i <- 1 to 3) {
       for (j <- 1 to 3) {
          println("i =" + i + " j=" + j)
       }
      }
    * */

    // 循环返回值   说明：将遍历过程中处理的结果返回到一个新 Vector 集合中，使用 yield 关键字。
    val res = for (i <- 1 to 10) yield i
    println(res)

    // 倒序打印
    for (i <- 1 to 10 reverse){
      println("reverse:"+i)
    }

  }

}
