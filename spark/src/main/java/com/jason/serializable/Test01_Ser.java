package com.jason.serializable;

import com.jason.bean.User;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年10月28日 17:08
 */
public class Test01_Ser {
    public static void main(String[] args) {
        // 1.创建配置对象
        SparkConf conf = new SparkConf().setMaster("local[*]").setAppName("sparkCore");

        // 2. 创建sparkContext
        JavaSparkContext sc = new JavaSparkContext(conf);

        // 3. 编写代码
        User zhangsan = new User("zhangsan", 13);
        User lisi = new User("lisi", 13);

        JavaRDD<User> userJavaRDD = sc.parallelize(Arrays.asList(zhangsan, lisi), 2);
        JavaRDD<User> mappedRDD = userJavaRDD.map(user -> new User(user.getName(), user.getAge() + 1));
        mappedRDD.collect().forEach(System.out::println);

        // 4. 关闭sc
        sc.stop();


    }
}
