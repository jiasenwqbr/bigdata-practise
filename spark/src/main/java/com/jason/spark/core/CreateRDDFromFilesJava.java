package com.jason.spark.core;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年10月24日 10:33
 */
public class CreateRDDFromFilesJava {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setAppName("create RDD from files").setMaster("local[*]");
        JavaSparkContext javaSparkContext = new JavaSparkContext(sparkConf);
        JavaRDD<String> lineJavaRDD = javaSparkContext.textFile("datas/wordcount/");
        JavaRDD<String> wordJavaRDD = lineJavaRDD.flatMap(line -> Arrays.asList(line.split(" ")).iterator());
        wordJavaRDD.collect().forEach(System.out::println);
    }
}
