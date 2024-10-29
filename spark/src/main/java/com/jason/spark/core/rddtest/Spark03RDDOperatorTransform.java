package com.jason.spark.core.rddtest;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年10月24日 17:54
 */
public class Spark03RDDOperatorTransform {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setMaster("local[*]").setAppName("Spark01 RDD Operator Transform");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        JavaRDD<Integer> parallelize = sc.parallelize(Arrays.asList(1, 2, 3, 4, 5,6), 2);
        JavaRDD<Integer> integerJavaRDD = parallelize.mapPartitions(new FlatMapFunction<Iterator<Integer>, Integer>() {
            @Override
            public Iterator<Integer> call(Iterator<Integer> iter) throws Exception {
                final Integer[] a = {new Integer(0)};
                iter.forEachRemaining(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) {
                        if (a[0] < integer) {
                            a[0] = integer;
                        }
                    }
                });
                return Arrays.asList(a[0]).iterator();
            }
        });
        integerJavaRDD.collect().forEach(System.out::println);
    }
}
