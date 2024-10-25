package com.jason.spark.core;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import java.util.Arrays;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年10月25日 17:54
 */
public class Spark09RDDSortBy {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setMaster("local[*]").setAppName("Spark09RDDSortBy");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        JavaRDD<Integer> parallelize = sc.parallelize(Arrays.asList(1, 2, 3, 4, 5, 6,5,4,3,3,3), 2);
        JavaRDD<Integer> javaRDD = parallelize.distinct();
        JavaRDD<Integer> sortByRDD = javaRDD.sortBy(new Function<Integer, Integer>() {
            @Override
            public Integer call(Integer integer) throws Exception {
                return integer;
            }
        }, true, 0);
        sortByRDD.collect().forEach(System.out::println);
        sc.stop();
    }
}
