package com.jason.spark.sql;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.api.java.function.ReduceFunction;
import org.apache.spark.sql.*;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年10月29日 14:59
 */
public class Test01_Method {
    public static void main(String[] args) {
        //1. 创建配置对象
        SparkConf conf = new SparkConf().setAppName("sparksql").setMaster("local[*]");
        //2. 获取sparkSession
        SparkSession sparkSession = SparkSession.builder().config(conf).getOrCreate();
        //3. 编写代码
        // 按照行读取
        Dataset<Row> lineDS = sparkSession.read().json("datas/user.json");
        // 转换为类对象
        //Dataset<User> userDS = lineDS.as(Encoders.bean(User.class));
        //userDS.show();
        Dataset<User> userDS = lineDS.map(new MapFunction<Row, User>() {
            @Override
            public User call(Row row) throws Exception {
                return new User(row.getLong(0),row.getString(1));
            }
        }, Encoders.bean(User.class));
        Dataset<User> sortDS = userDS.sort(new Column("age"));
        sortDS.show();
        RelationalGroupedDataset groupedDS = userDS.groupBy(new Column("name"));
        Dataset<Row> count = groupedDS.count();
        count.show();

        KeyValueGroupedDataset<String, User> stringUserKeyValueGroupedDataset1 = userDS.groupByKey(new MapFunction<User, String>() {
            @Override
            public String call(User user) throws Exception {
                return user.toString();
            }
        }, Encoders.STRING());

        long count1 = userDS.count();
        Dataset<Long> map = userDS.map(new MapFunction<User, Long>() {
            @Override
            public Long call(User user) throws Exception {
                return user.getAge();
            }
        }, Encoders.LONG());
        Long reduce = map.reduce(new ReduceFunction<Long>() {
            @Override
            public Long call(Long aLong, Long t1) throws Exception {
                return aLong + t1;
            }
        });

        System.out.println(reduce/count1);


        sparkSession.close();
    }
}
