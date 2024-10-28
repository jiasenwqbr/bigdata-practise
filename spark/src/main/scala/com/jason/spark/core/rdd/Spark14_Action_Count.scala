package com.jason.spark.core

import org.apache.spark.{SparkConf, SparkContext}

object Spark14_Action_Count {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Spark11_KeyValue_Operation")
    val sc = new SparkContext(sparkConf)
    val dataRDD = sc.makeRDD(List(1, 2, 3, 4))
    val l = dataRDD.count()
    println(l)
  }

}
