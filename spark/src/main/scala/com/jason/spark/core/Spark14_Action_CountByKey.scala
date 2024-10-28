package com.jason.spark.core

import org.apache.spark.{SparkConf, SparkContext}

object Spark14_Action_CountByKey {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Spark11_KeyValue_Operation")
    val sc = new SparkContext(sparkConf)
    val dataRDD = sc.makeRDD(List(("a", 8),("a", 8),("b", 9),("c", 10)))
    val stringToLong = dataRDD.countByKey()
    stringToLong.foreach(println)
  }

}
