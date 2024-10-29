package com.jason.spark.core.rddtest;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年10月25日 11:04
 */
public class Spark04RDDOperatorTransform {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setMaster("local[*]").setAppName("Spark01 RDD Operator Transform");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        JavaRDD<Integer> parallelize = sc.parallelize(Arrays.asList(1, 2, 3, 4, 5,6), 2);
        JavaRDD<Integer> integerJavaRDD = parallelize.mapPartitionsWithIndex(new Function2<Integer, Iterator<Integer>, Iterator<Integer>>() {
            Integer a = 0;
            @Override
            public Iterator<Integer> call(Integer index, Iterator<Integer> integerIterator) throws Exception {
                if (index==0){
                    integerIterator.forEachRemaining(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) {
                            if (a<integer){
                                a = integer;
                            }
                        }
                    });
                    return Arrays.asList(a).iterator();
                }
                return new ArrayList<Integer>().iterator();
            }
        },false);
        integerJavaRDD.collect().forEach(System.out::println);
        sc.stop();
    }
}
