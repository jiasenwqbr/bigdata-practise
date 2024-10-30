package com.jason.spark.sql;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;

import java.util.function.Function;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年10月30日 13:57
 */
public class Test06_CSV {
    public static void main(String[] args) {
        //1. 创建配置对象
        SparkConf conf = new SparkConf().setAppName("sparksql").setMaster("local[*]");

        //2. 获取sparkSession
        SparkSession spark = SparkSession.builder().config(conf).getOrCreate();

        //3. 编写代码
        DataFrameReader reader = spark.read();
        // 添加参数  读取csv
        Dataset<Row> userDS = reader
                .option("header", "true") //默认为false 不读取列名
                .option("sep", ",") // 默认为, 列的分割
                // 不需要写压缩格式  自适应
                .csv("datas/user.csv");
        userDS.show();
        // 转换为user的ds
        // 直接转换类型会报错  csv读取的数据都是string
//        Dataset<User> userDS1 = userDS.as(Encoders.bean(User.class));
        userDS.printSchema();
        Dataset<User> mapedUserDS = userDS.map(new MapFunction<Row, User>() {

            @Override
            public User call(Row row) throws Exception {
                return new User(Long.valueOf(row.getString(1)), row.getString(0));
            }
        }, Encoders.bean(User.class));

        mapedUserDS.show();
        // 写出为csv文件
        DataFrameWriter<User> write = mapedUserDS.write();
        write.option("sep","||")
                .option("header",true)
                //.option("compression","gzip")// 压缩格式
                .mode(SaveMode.Append)
                        .csv("output");



        //4. 关闭sparkSession
        spark.close();

    }
}
