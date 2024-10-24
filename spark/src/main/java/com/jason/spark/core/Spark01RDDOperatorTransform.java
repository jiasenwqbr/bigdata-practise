package com.jason.spark.core;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年10月24日 15:41
 */
public class Spark01RDDOperatorTransform {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setMaster("local[*]").setAppName("Spark01RDDOperatorTransform");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        JavaRDD<Integer> parallelize = sc.parallelize(Arrays.asList(1, 2, 3, 4, 5));
        JavaRDD<Integer> mappedRDD = parallelize.map(num -> num * 2);
        mappedRDD.collect().forEach(System.out::println);
        sc.stop();
    }
}
