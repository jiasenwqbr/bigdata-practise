package com.jason.spark.sql;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.*;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年10月30日 14:32
 */
public class Test07_JSON {
    public static void main(String[] args) {
        //1. 创建配置对象
        SparkConf conf = new SparkConf().setAppName("sparksql").setMaster("local[*]");

        //2. 获取sparkSession
        SparkSession spark = SparkSession.builder().config(conf).getOrCreate();

        //3. 编写代码
        Dataset<Row> json = spark.read().json("datas/user.json");
        // json数据可以读取数据的数据类型
        Dataset<User> userDS = json.as(Encoders.bean(User.class));
        userDS.show();
        DataFrameWriter<User> write = userDS.write();
        write.json("output");
        spark.close();
    }

}
