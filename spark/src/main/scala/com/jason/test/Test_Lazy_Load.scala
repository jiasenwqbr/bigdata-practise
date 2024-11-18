package com.jason.test

object Test_Lazy_Load {
  def main(args: Array[String]): Unit = {
    lazy val res = sum(10,20)

    println("----------------------")
    println("res="+res)
  }

  def sum(n1:Int,n2:Int) :Int = {
    println("sum 被执行...")
    n1+n2
  }

}
