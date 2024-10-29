package com.jason.spark.core.rddtest;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Map;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年10月28日 15:17
 */
public class Spark14ActionCountByKey {
    public static void main(String[] args) {
        // 1.创建配置对象
        SparkConf conf = new SparkConf().setMaster("local[*]").setAppName("sparkCore");

        // 2. 创建sparkContext
        JavaSparkContext sc = new JavaSparkContext(conf);

        // 3. 编写代码
        JavaPairRDD<String, Integer> pairRDD = sc.parallelizePairs(Arrays.asList(new Tuple2<>("a", 8), new Tuple2<>("b", 8), new Tuple2<>("a", 8), new Tuple2<>("d", 8)));
        Map<String, Long> stringLongMap = pairRDD.countByKey();
        for (String s : stringLongMap.keySet()){
            System.out.println(s+"   "+stringLongMap.get(s));
        }
    }
}
