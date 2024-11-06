package com.jason.test

object TestValueTransfer {
  def main(args: Array[String]): Unit = {
    //（1）自动提升原则：有多种类型的数据混合运算时，系统首先自动将所有
    // 数据转换成精度大的那种数值类型，然后再进行计算。
    var n = 1+2.0
    println(n)
    //（2）把精度大的数值类型赋值给精度小的数值类型时，就会报错，反之就会进行自动类型转换。
    var n2 = 1.0
    // var n3:Int = n2;

    //（3）（byte，short）和 char 之间不会相互自动转换。
    var n4 : Byte = 1
    // var c1 : Char = n4 //错误
    var n5:Int = n4

    //（4）byte，short，char 他们三者可以计算，在计算时首先转换为 int 类型。
    var n6 : Byte = 1
    var c2 : Char = 1
   //  var n7 : Short = n6 + c2  //当 n6 + c2 结果类型就是 int
    var n7 = n6.toInt + c2.toInt;
    println(n7)
    // var n8 : Short = 10 + 90 //错误



  }

}
