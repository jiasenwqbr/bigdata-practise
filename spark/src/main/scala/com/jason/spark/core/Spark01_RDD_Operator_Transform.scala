package com.jason.spark.core

import org.apache.spark.{SparkConf, SparkContext}

object Spark01_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("create RDD from files")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(1, 2, 3, 4, 5, 6))
    // val rdd2 = rdd.map(num => num * 2 )
    val rdd2 = rdd.map(_ * 2 )
    rdd2.collect().foreach(println)
    sc.stop()
  }

}
