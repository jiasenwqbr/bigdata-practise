package com.jason.spark.core;

import com.jason.spark.core.bean.CategoryCountInfo;
import com.jason.spark.core.bean.UserVisitAction;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年10月29日 13:14
 */
public class Test06_Top10 {
    public static void main(String[] args) throws ClassNotFoundException {
        // 1.创建配置对象
        SparkConf conf = new SparkConf().setMaster("local[*]").setAppName("sparkCore")
                // 替换默认的序列化机制
                .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
                // 注册需要使用kryo序列化的自定义类
                .registerKryoClasses(new Class[]{Class.forName("com.jason.spark.core.bean.CategoryCountInfo"),Class.forName("com.jason.spark.core.bean.UserVisitAction")});

        // 2. 创建sparkContext
        JavaSparkContext sc = new JavaSparkContext(conf);
        // 3. 编写代码
        // 分三次进行wordCount 最后使用cogroup合并三组数据
        JavaRDD<String> lineRDD = sc.textFile("datas/user_visit_action.txt");
        // 转换为对象
        JavaRDD<UserVisitAction> actionRDD = lineRDD.map(new Function<String, UserVisitAction>() {
            @Override
            public UserVisitAction call(String s) throws Exception {
                String[] data = s.split("_");
                return new UserVisitAction(
                        data[0],
                        data[1],
                        data[2],
                        data[3],
                        data[4],
                        data[5],
                        data[6],
                        data[7],
                        data[8],
                        data[9],
                        data[10],
                        data[11],
                        data[12]
                );
            }
        });

        JavaRDD<CategoryCountInfo> categoryCountInfoJavaRDD = actionRDD.flatMap(new FlatMapFunction<UserVisitAction, CategoryCountInfo>() {
            @Override
            public Iterator<CategoryCountInfo> call(UserVisitAction userVisitAction) throws Exception {
                ArrayList<CategoryCountInfo> countInfos = new ArrayList<>();
                if (!userVisitAction.getClick_category_id().equals("-1")) {
                    // 当前为点击数据
                    countInfos.add(new CategoryCountInfo(userVisitAction.getClick_category_id(), 1L, 0L, 0L));
                } else if (!userVisitAction.getOrder_category_ids().equals("null")) {
                    // 当前为订单数据
                    String[] orders = userVisitAction.getOrder_category_ids().split(",");
                    for (String order : orders) {
                        countInfos.add(new CategoryCountInfo(order, 0L, 1L, 0L));
                    }
                } else if (!userVisitAction.getPay_category_ids().equals("null")) {
                    // 当前为支付数据
                    String[] pays = userVisitAction.getPay_category_ids().split(",");
                    for (String pay : pays) {
                        countInfos.add(new CategoryCountInfo(pay, 0L, 0L, 1L));
                    }
                }
                return countInfos.iterator();
            }
        });
        // 合并相同的id
        JavaPairRDD<String, CategoryCountInfo> stringCategoryCountInfoJavaPairRDD = categoryCountInfoJavaRDD.mapToPair(new PairFunction<CategoryCountInfo, String, CategoryCountInfo>() {
            @Override
            public Tuple2<String, CategoryCountInfo> call(CategoryCountInfo categoryCountInfo) throws Exception {
                return new Tuple2<>(categoryCountInfo.getCategoryId(), categoryCountInfo);
            }
        });

        JavaPairRDD<String, CategoryCountInfo> countInfoPairRDD  = stringCategoryCountInfoJavaPairRDD.reduceByKey(new Function2<CategoryCountInfo, CategoryCountInfo, CategoryCountInfo>() {
            @Override
            public CategoryCountInfo call(CategoryCountInfo v1, CategoryCountInfo v2) throws Exception {
                v1.setClickCount(v1.getClickCount() + v2.getClickCount());
                v1.setOrderCount(v1.getOrderCount() + v2.getOrderCount());
                v1.setPayCount(v1.getPayCount() + v2.getPayCount());
                return v1;
            }
        });
        JavaRDD<CategoryCountInfo> countInfoJavaRDD  = countInfoPairRDD.map(new Function<Tuple2<String, CategoryCountInfo>, CategoryCountInfo>() {
            @Override
            public CategoryCountInfo call(Tuple2<String, CategoryCountInfo> v1) throws Exception {

                return v1._2;
            }
        });
        JavaRDD<CategoryCountInfo> result = countInfoJavaRDD.sortBy(new Function<CategoryCountInfo, CategoryCountInfo>() {
            @Override
            public CategoryCountInfo call(CategoryCountInfo v) throws Exception {
                return v;
            }
        }, false, 2);

        //result.collect().forEach(System.out::println);
        result.take(10).forEach(System.out::println);


        sc.stop();


    }
}
