package com.jason.spark.sql;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import static org.apache.spark.sql.functions.col;
/**
 * @Description:
 * @author: 贾森
 * @date: 2024年10月29日 17:00
 */
public class Test02_SQL {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setAppName("spark sql").setMaster("local[*]");
        SparkSession sparkSession = SparkSession.builder().config(sparkConf).getOrCreate();
        //3. 编写代码
        // 按照行读取
        Dataset<Row> lineDS = sparkSession.read().json("datas/user.json");
        // 创建视图 => 转换为表格 填写表名
        // 临时视图的生命周期和当前的sparkSession绑定
        // orReplace表示覆盖之前相同名称的视图
        lineDS.createOrReplaceTempView("t1");
        Dataset<Row> result = sparkSession.sql("select name,age  from t1 where age>17");
        result.show();
        Dataset<Row> result2 = lineDS.select(col("name"), col("age").plus(1).as("newAge")).filter(col("age").lt(20));
        result2.show();

        sparkSession.close();


    }
}
