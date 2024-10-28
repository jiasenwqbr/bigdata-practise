package com.jason.spark.core

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object Spark11_KeyValue_Operation {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Spark11_KeyValue_Operation")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(("k","v"),("k1","v1"),("k2","v2"),("k3","v3")),2)
    //rdd.map((_,_))
    val value = rdd.mapValues(v => v.concat("|||"))
    value.collect.foreach(println)

    sc.stop()
  }

}
