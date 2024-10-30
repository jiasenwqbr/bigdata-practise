package com.jason.spark.sql;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.expressions.UserDefinedFunction;
import org.apache.spark.sql.types.DataTypes;

import static org.apache.spark.sql.functions.udf;
/**
 * @Description:
 * @author: 贾森
 * @date: 2024年10月30日 10:51
 */
public class Test04_UDF {
    public static void main(String[] args) {
        //1. 创建配置对象
        SparkConf conf = new SparkConf().setAppName("sparksql").setMaster("local[*]");

        //2. 获取sparkSession
        SparkSession spark = SparkSession.builder().config(conf).getOrCreate();

        //3. 编写代码
        Dataset<Row> lineRDD = spark.read().json("datas/user.json");

        lineRDD.createOrReplaceTempView("user");
        // 定义一个函数
        // 需要首先导入依赖import static org.apache.spark.sql.functions.udf;
        UserDefinedFunction addName = udf(new UDF1<String, String>() {
            @Override
            public String call(String s) throws Exception {
                return s+"大侠";
            }
        }, DataTypes.StringType);
        spark.udf().register("addName",addName);
        spark.sql("select addName(name) newName from user").show();


        spark.close();

    }
}
