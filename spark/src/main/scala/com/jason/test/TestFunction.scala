package com.jason.test
/*
*
* 函数至简原则：能省则省
    （1）return 可以省略，Scala 会使用函数体的最后一行代码作为返回值
    （2）如果函数体只有一行代码，可以省略花括号
    （3）返回值类型如果能够推断出来，那么可以省略（:和返回值类型一起省略）
    （4）如果有 return，则不能省略返回值类型，必须指定
    （5）如果函数明确声明 unit，那么即使函数体中使用 return 关键字也不起作用
    （6）Scala 如果期望是无返回值类型，可以省略等号
    （7）如果函数无参，但是声明了参数列表，那么调用时，小括号，可加可不加
    （8）如果函数没有参数列表，那么小括号可以省略，调用时小括号必须省略
    （9）如果不关心名称，只关心逻辑处理，那么函数名（def）可以省略
* */
object TestFunction {
  def main(args: Array[String]): Unit = {
    def f (s:String) : String  = {
      return s+"hello"
    }

    //（1） return 可以省略,Scala 会使用函数体的最后一行代码作为返回值
    def f1(s:String):String = {
      s+"hello"
    }

    //（2）如果函数体只有一行代码，可以省略花括号
    def f2(s:String) : String = s + "hello"

    //（3）返回值类型如果能够推断出来，那么可以省略（:和返回值类型一起省略）
    def f3(s:String) = s+"Hello"

    //（4）如果有 return，则不能省略返回值类型，必须指定。
    def f4() :String = {
      return "ximenqing4"
    }

    //（5）如果函数明确声明 unit，那么即使函数体中使用 return 关键字也不起作用
    def f5(): Unit = {
      return "dalang5"
    }

    //（6）Scala 如果期望是无返回值类型,可以省略等号
    // 将无返回值的函数称之为过程
    def f6() {
      "dalang6"
    }
    println(f6())
    //（7）如果函数无参，但是声明了参数列表，那么调用时，小括号，可加可 不加
    def f7() = "dalang7"
    println(f7())
    println(f7)

    //（8）如果函数没有参数列表，那么小括号可以省略,调用时小括号必须省略
    def f8 = "dalang"
    //println(f8())
    println(f8)
    //（9）如果不关心名称，只关心逻辑处理，那么函数名（def）可以省略
    def f9 = (x:String)=>{println("wusong")}
    def f10(f:String=>Unit) = {
      f("")
    }
    f10(f9)
    println(f10((x:String)=>{println("wusong")}))





  }

}
