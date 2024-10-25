package com.jason.spark.core

import org.apache.spark.{SparkConf, SparkContext}

object Spark09_RDDSort_By {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Spark06_RDD_GroupBy")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 5, 4, 3, 3, 3),2)
    val distinctedRDD = rdd.distinct()
    val sortedRDD = distinctedRDD.sortBy(v => v,true,0)
    sortedRDD.collect().foreach(println)
  }

}
