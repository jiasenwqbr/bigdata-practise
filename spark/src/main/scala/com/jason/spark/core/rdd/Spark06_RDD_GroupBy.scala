package com.jason.spark.core

import org.apache.spark.{SparkConf, SparkContext}

object Spark06_RDD_GroupBy {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Spark06_RDD_GroupBy")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(1, 2, 3, 4, 5, 6),2)
    val groupbyRDD = rdd.groupBy(v => v % 2)
    groupbyRDD.collect().foreach(println)

    val groupbyRDD1 = rdd.groupBy(v => v % 2 == 0)
    groupbyRDD1.collect().foreach(println)

  }

}
