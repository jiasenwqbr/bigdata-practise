package com.jason.spark.core

import org.apache.spark.{SparkConf, SparkContext}

object Spark08_RDD_Distinct {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Spark06_RDD_GroupBy")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 5, 4, 3, 3, 3),2)
    val distinctedRDD = rdd.distinct()
    distinctedRDD.collect().foreach(println)
  }

}
