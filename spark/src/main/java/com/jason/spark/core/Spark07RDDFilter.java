package com.jason.spark.core;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年10月25日 17:43
 */
public class Spark07RDDFilter {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setMaster("local[*]").setAppName("Spark01 RDD Operator Transform");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        JavaRDD<Integer> parallelize = sc.parallelize(Arrays.asList(1, 2, 3, 4, 5, 6), 2);
        JavaRDD<Integer> filteredRDD = parallelize.filter(v -> v % 2 == 0);
        filteredRDD.collect().forEach(System.out::println);
        sc.stop();
    }
}
