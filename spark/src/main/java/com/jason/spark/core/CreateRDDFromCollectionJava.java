package com.jason.spark.core;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年10月24日 09:51
 */
public class CreateRDDFromCollectionJava {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setMaster("local[*]").setAppName("create RDD from collection");
        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);
        JavaRDD<Integer> rdd1 = sparkContext.parallelize(Arrays.asList(1,2,3,4,5),2);
        List<Integer> collect = rdd1.collect();
        for (Integer str : collect){
            System.out.println(str);
        }

        sparkContext.stop();
    }
}
