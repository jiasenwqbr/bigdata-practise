package com.jason.spark.core

import org.apache.spark.{SparkConf, SparkContext}

object Spark12_GroupByKey_Operation {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Spark12_GroupByKey_Operation")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List("hi", "hi", "hello", "spark"),2)
    val mapedRDD = rdd.map((_, 1))
//    val groupRDD = mapedRDD.groupByKey()
//    val value = groupRDD.mapValues(a => a.reduce(_+_))
//    value.collect().foreach(println)
//    sc.stop()
    val value = mapedRDD.reduceByKey(_ + _)
    value.collect().foreach(println)
    sc.stop()
  }

}
