package com.jason.spark.core;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;

import java.util.*;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年10月25日 11:36
 */
public class Spark05RDDFlatMap {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setMaster("local[*]").setAppName("Spark05RDDFlatMap");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList( 4, 5,6);
        List<Integer> list3 = Arrays.asList(7,8,9);
        List<List<Integer>> list = new ArrayList<>();
        list.add(list1);
        list.add(list2);
        list.add(list3);

//        JavaRDD<List<List<Integer>>> rdd = sc.parallelize(Collections.singletonList(list), 2);
//
//        JavaRDD<Integer> objectJavaRDD = rdd.flatMap(new FlatMapFunction<List<List<Integer>>, Integer>() {
//            List<Integer> li = new ArrayList<>();
//            @Override
//            public Iterator<Integer> call(List<List<Integer>> lists) throws Exception {
//                for (List<Integer> l : lists){
//                    for (Integer integer : l){
//                        li.add(integer);
//                    }
//                }
//                return li.iterator();
//            }
//        });
        JavaRDD<List<Integer>> parallelize = sc.parallelize(list, 2);
        JavaRDD<Integer> integerJavaRDD = parallelize.flatMap(new FlatMapFunction<List<Integer>, Integer>() {
            @Override
            public Iterator<Integer> call(List<Integer> integers) throws Exception {
                return integers.iterator();
            }
        });
        integerJavaRDD.collect().forEach(System.out::println);
        sc.stop();
    }
}
