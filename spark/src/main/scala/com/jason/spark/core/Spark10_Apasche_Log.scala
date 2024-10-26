package com.jason.spark.core

import org.apache.spark.{SparkConf, SparkContext}

import java.text.SimpleDateFormat

object Spark10_Apasche_Log {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("create RDD from collection")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.textFile("datas/apache.log")
    val timeRDD = rdd.map(
      line => {
        val datas = line.split(" ")
        val time = datas(3)
        val sdf = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss")
        val date = sdf.parse(time)
        val sdf1 = new SimpleDateFormat("HH")
        val hour = sdf1.format(date)
        (hour, 1)
      }).groupBy(_._1)
    timeRDD.map{
      case (hour,iter) => {
        (hour,iter.size)
      }
    }.collect.foreach(println)
    sc.stop()


  }

}
