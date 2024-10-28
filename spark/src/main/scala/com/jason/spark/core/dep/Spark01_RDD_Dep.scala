package com.jason.spark.core.dep

import org.apache.spark.{SparkConf, SparkContext}

object Spark01_RDD_Dep {
  def main(args: Array[String]): Unit = {
    // 创建spark运行配置对象
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("WordCount")

    // 创建spark上下文环境对象
    val sc = new SparkContext(sparkConf)

    // 读取文件数据
    val fileRdd = sc.textFile("datas/wordcount/")
    println(fileRdd.toDebugString)
    println("***********************************")
    // 对文件进行分词
    val wordRdd = fileRdd.flatMap(_.split(" "))
    println(wordRdd.toDebugString)
    println("***********************************")
    // 转换结构 word => (word, 1)   wordRdd.map(word => (word,1))
    val word2OneRdd = wordRdd.map((_, 1))
    println(word2OneRdd.toDebugString)
    println("***********************************")

    // 将转换结构后的数据按照相同的单词进行分组聚合
    // val word2CountRdd = word2OneRdd.reduceByKey(_ + _)
    val word2CountRdd =  word2OneRdd.reduceByKey((a,b) => a+b)
    println(word2CountRdd.toDebugString)
    println("***********************************")
    // 将数据聚合结果采集到内存中
    val word2Count = word2CountRdd.collect()

    // 打印结果
    word2Count.foreach(println)

    // 关闭Spark
    sc.stop()
  }

}
