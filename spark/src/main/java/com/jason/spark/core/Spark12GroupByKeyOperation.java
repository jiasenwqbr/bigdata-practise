package com.jason.spark.core;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.Arrays;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年10月28日 10:24
 */
public class Spark12GroupByKeyOperation {
    public static void main(String[] args) {
        // 1.创建配置对象
        SparkConf conf = new SparkConf().setMaster("local[*]").setAppName("sparkCore");
        // 2. 创建sparkContext
        JavaSparkContext sc = new JavaSparkContext(conf);
        // 3. 编写代码
        JavaRDD<String> integerJavaRDD = sc.parallelize(Arrays.asList("hi","hi","hello","spark" ),2);
        JavaPairRDD<String, Integer> stringIntegerJavaPairRDD = integerJavaRDD.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<>(s, 1);
            }
        });
//        JavaPairRDD<String, Iterable<Integer>> stringIterableJavaPairRDD = stringIntegerJavaPairRDD.groupByKey();
//
//        JavaPairRDD<String, Integer> stringIntegerJavaPairRDD1 = stringIterableJavaPairRDD.mapValues(new Function<Iterable<Integer>, Integer>() {
//            @Override
//            public Integer call(Iterable<Integer> integers) throws Exception {
//                int sum = 0;
//                for (Integer i : integers) {
//                    sum += i;
//                }
//                return sum;
//            }
//        });
//        stringIntegerJavaPairRDD1.collect().forEach(System.out::println);
        JavaPairRDD<String, Integer> stringIntegerJavaPairRDD1 = stringIntegerJavaPairRDD.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) throws Exception {
                return integer + integer2;
            }
        });

        stringIntegerJavaPairRDD1.collect().forEach(System.out::println);


    }
}
