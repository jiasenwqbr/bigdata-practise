package com.jason.test

object TestStringTransfer {
  def main(args: Array[String]): Unit = {
    //（1）基本类型转 String 类型（语法：将基本类型的值+"" 即可）
    var str1 : String = true + ""
    var str2 : String = 4.5 + ""
    var str3 : String = 100 +""
    //（2）String 类型转基本数值类型（语法：调用相关 API）
    var s1 : String = "12"
    var n1 : Byte = s1.toByte
    var n2 : Short = s1.toShort
    var n3 : Int = s1.toInt
    var n4 : Long = s1.toLong

    println(str1)
    println(str2)
    println(str3)
    println(n1)
    println(n2)
    println(n3)
    println(n4)
    println(9999999999L.toShort)
  }

}
