package com.jason.spark.core

import org.apache.spark.{SparkConf, SparkContext}

object Spark07_RDD_Filter {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Spark06_RDD_GroupBy")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(1, 2, 3, 4, 5, 6),2)
    val filteredRDD = rdd.filter(v => v % 2 == 0)
    filteredRDD.collect().foreach(println)
    sc.stop()
  }

}
