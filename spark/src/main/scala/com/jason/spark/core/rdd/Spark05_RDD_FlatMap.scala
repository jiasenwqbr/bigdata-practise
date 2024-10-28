package com.jason.spark.core

import org.apache.spark.{SparkConf, SparkContext}

object Spark05_RDD_FlatMap {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Spark05_RDD_FlatMap")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(
      List(1,2),List(3,4)
    ),1)
    val dataRDD = rdd.flatMap(list => list)
    dataRDD.collect().foreach(println)
    sc.stop()
  }

}
