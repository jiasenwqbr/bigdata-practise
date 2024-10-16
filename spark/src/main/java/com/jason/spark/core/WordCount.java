package com.jason.spark.core;

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
import java.util.function.Consumer;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年10月16日 12:38
 */
public class WordCount {
    public static void main(String[] args) {
        // 1.创建配置对象
        SparkConf conf = new SparkConf().setMaster("local[*]").setAppName("WordCount");
        // 2.创建sparkContext
        JavaSparkContext sc = new JavaSparkContext(conf);
        // 3.读取文件数据
        JavaRDD<String> fileJavaRDD = sc.textFile("datas/wordcount/");
        // 4.对文件进行分词
        JavaRDD<String> flatMapRDD = fileJavaRDD.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String s) throws Exception {
                String[] s1 = s.split(" ");
                List<String> list = Arrays.asList(s1);
                return list.iterator();
            }
        });
        //  5.转换结构
        JavaPairRDD<String, Integer> stringIntegerJavaPairRDD = flatMapRDD.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<>(s, 1);
            }
        });

        // 6.合并相同的单词
        JavaPairRDD<String, Integer> javaPairRDD  = stringIntegerJavaPairRDD.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });

        // 7.将数据聚合结果采集到内存中
        // javaPairRDD.collect().forEach(System.out::println);
        javaPairRDD.collect().forEach(new Consumer<Tuple2<String, Integer>>() {
            @Override
            public void accept(Tuple2<String, Integer> stringIntegerTuple2) {
                System.out.println(stringIntegerTuple2._1+"  "+stringIntegerTuple2._2);
            }
        });

        // 8.关闭
        sc.close();
    }
}
