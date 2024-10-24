package com.jason.spark.core

import org.apache.spark.{SparkConf, SparkContext}

object CreateRDDFromFiles {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("create RDD from files")
    val sc = new SparkContext(sparkConf)
    val fileRDD = sc.textFile("datas/wordcount/")
    fileRDD.foreach(println)
    val wordRdd = fileRDD.flatMap(_.split(" "))
    // wordRdd.foreach(println)
    wordRdd.collect().foreach(println)
    sc.stop()
  }
}
