package com.jason.spark.core

import org.apache.spark.{SparkConf, SparkContext}

object CreateRDDFromCollection {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("create RDD from collection")
    val sparkContext = new SparkContext(sparkConf)
    val rdd1 = sparkContext.parallelize(List(1, 2, 3, 4))
    val rdd2 = sparkContext.makeRDD(List(1, 2, 3, 4, 5))
    rdd1.collect().foreach(println)
    rdd2.collect().foreach(println)
    sparkContext.stop()

  }

}
