package com.jason.test

import scala.collection.mutable.ArrayBuffer

object Test_Higher_Order_Functions {
  def main(args: Array[String]): Unit = {
    // (1)映射
    def map(arr:Array[Int],op:Int => Int) : Unit = {
      for (elem <- arr) yield  op(elem)
    }

    val arr1 = Array(1,2,3,4,5,6)
    map(arr1,el => el + 10)
    println(arr1.mkString(","))

    val arr = Array(1, 2, 3, 4)
    map(arr, (x: Int) => {
      x * x
    })
    println(arr.mkString(","))

    //  （2）filter 过滤。有参数，且参数再后面只使用一次，则参数省略且后面参数用_表示
    def filter(arr:Array[Int],op:Int => Boolean) = {
      var arr1:ArrayBuffer[Int] = ArrayBuffer[Int]()
      for (elem <- arr if op(elem)){
        arr1.append(elem)
      }
      arr1.toArray
    }

    var arr3 = Array(1,2,3,4,5,6)
    println( filter(arr3,elem => elem > 3).mkString(","))
    println( filter(arr3,_ < 4).mkString(","))

    // （3）reduce 聚合。有多个参数，且每个参数再后面只使用一次，则参数省略且后面参数用_表示，第 n 个_代表第 n 个参数

    def reduce(arr:Array[Int],op:(Int,Int) => Int) = {
      var init:Int  = arr(0)
      for (elem <- 1 until arr.length) {
        init = op(init,arr(elem))
      }
      init
    }
    var arr4 = Array(1,10,10,10,10)
    val ints = reduce(arr4, _ * _)
    println(ints)




  }

}
