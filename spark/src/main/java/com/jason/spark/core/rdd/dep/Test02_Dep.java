package com.jason.spark.core.rdd.dep;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年10月29日 09:35
 */
public class Test02_Dep {
    public static void main(String[] args) throws InterruptedException {
        // 1.创建配置对象
        SparkConf conf = new SparkConf().setMaster("local[*]").setAppName("sparkCore");

        // 2. 创建sparkContext
        JavaSparkContext sc = new JavaSparkContext(conf);

        // 3. 编写代码
        JavaRDD<String> lineRDD = sc.textFile("datas/wordcount/2.txt");
        System.out.println(lineRDD.toDebugString());
        System.out.println("-------------------");
        JavaRDD<String> wordRDD = lineRDD.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String s) throws Exception {
                List<String> stringList = Arrays.asList(s.split(" "));
                return stringList.iterator();
            }
        });
        System.out.println(wordRDD);
        System.out.println("-------------------");

        JavaPairRDD<String, Integer> tupleRDD = wordRDD.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<>(s, 1);
            }
        });
        System.out.println(tupleRDD.toDebugString());
        System.out.println("-------------------");

        // 缩减分区
        JavaPairRDD<String, Integer> coalesceRDD = tupleRDD.coalesce(1);

        JavaPairRDD<String, Integer> wordCountRDD = coalesceRDD.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        },4);
        System.out.println(wordCountRDD.toDebugString());
        System.out.println("-------------------");

        wordCountRDD. collect().forEach(System.out::println);
        wordCountRDD. collect().forEach(System.out::println);

        Thread.sleep(600000);

        // 4. 关闭sc
        sc.stop();

    }
}
