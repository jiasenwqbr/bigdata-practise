package com.jason.spark.core.partitioner;

import org.apache.spark.Partitioner;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.Optional;
import scala.Tuple2;

import java.util.Arrays;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年10月29日 10:35
 */
public class Test01_Partitioner {
    public static void main(String[] args) {
        // 1.创建配置对象
        SparkConf conf = new SparkConf().setMaster("local[*]").setAppName("sparkCore");

        // 2. 创建sparkContext
        JavaSparkContext sc = new JavaSparkContext(conf);

        // 3. 编写代码
        JavaPairRDD<String, Integer> tupleRDD = sc.parallelizePairs(Arrays.asList(new Tuple2<>("s", 1), new Tuple2<>("a", 3), new Tuple2<>("d", 2)));
        // 获取分区器
        Optional<Partitioner> partitioner = tupleRDD.partitioner();
        System.out.println(partitioner);

        JavaPairRDD<String, Integer> reduceByKeyRDD = tupleRDD.reduceByKey((v1, v2) -> v1 + v2);
        // 获取分区器
        Optional<Partitioner> partitioner1 = reduceByKeyRDD.partitioner();
        System.out.println(partitioner1);

        // 4. 关闭sc
        sc.stop();



    }
}
