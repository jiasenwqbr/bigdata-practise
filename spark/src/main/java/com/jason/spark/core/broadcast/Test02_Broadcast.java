package com.jason.spark.core.broadcast;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;

import java.util.Arrays;
import java.util.List;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年10月29日 10:56
 */
public class Test02_Broadcast {
    public static void main(String[] args) {
        // 1.创建配置对象
        SparkConf sparkConf = new SparkConf().setMaster("local[*]").setAppName("broadcast");

        // 2.创建sparkContext
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        // 3.编写代码
        JavaRDD<Integer> intRDD = sc.parallelize(Arrays.asList(4, 56, 7, 8, 1, 2));

        // 幸运数字
        List<Integer> list = Arrays.asList(1,2,3,4,5);
        // 找出幸运数字
        // 每一个task都会创建一个list浪费内存
        /*JavaRDD<Integer> result = intRDD.filter(v -> list.contains(v));
        result.collect().forEach(System.out::println);*/
        // 创建广播变量
        // 只发送一份数据到每一个executor

        Broadcast<List<Integer>> broadcast = sc.broadcast(list);
        JavaRDD<Integer> result = intRDD.filter(v -> broadcast.value().contains(v));
        result.collect().forEach(System.out::println);

        sc.stop();


    }
}
