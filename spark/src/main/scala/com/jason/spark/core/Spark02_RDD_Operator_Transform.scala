package com.jason.spark.core

import org.apache.spark.{SparkConf, SparkContext}

object Spark02_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("create RDD from files")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(1, 2, 3, 4, 5, 6),2)

    val mapPartitions = rdd.mapPartitions(
      iter => {
        iter.map(_ * 2)
      }
    )
    mapPartitions.collect().foreach(println)
  }

}
