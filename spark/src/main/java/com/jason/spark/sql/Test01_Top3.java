package com.jason.spark.sql;

import lombok.Data;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.*;
import org.apache.spark.sql.expressions.Aggregator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.function.BiConsumer;
import static org.apache.spark.sql.functions.udaf;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年10月30日 21:36
 */

    /*      （1）查询出来所有的点击记录，并与city_info表连接，得到每个城市所在的地区，与 Product_info表连接得到商品名称。
            （2）按照地区和商品名称分组，统计出每个商品在每个地区的总点击次数。
            （3）每个地区内按照点击次数降序排列。
            （4）只取前三名，并把结果保存在数据库中。
            （5）城市备注需要自定义UDAF函数。
    */
public class Test01_Top3 {
    public static void main(String[] args) {
        System.setProperty("HADOOP_USER_NAME","jason");
        // 1. 创建sparkConf配置对象
        SparkConf conf = new SparkConf().setAppName("sql").setMaster("local[*]");

        // 2. 创建sparkSession连接对象
        SparkSession spark = SparkSession.builder().enableHiveSupport().config(conf).getOrCreate();
        // spark.sql("show tables").show();
        // 3. 编写代码
        // 将3个表格数据join在一起
        Dataset<Row> t1DS = spark.sql("select \n" +
                "\tc.area,\n" +
                "\tc.city_name,\n" +
                "\tp.product_name\n" +
                "from\n" +
                "\tuser_visit_action u\n" +
                "join\n" +
                "\tcity_info c\n" +
                "on\n" +
                "\tu.city_id=c.city_id\n" +
                "join\n" +
                "\tproduct_info p\n" +
                "on\n" +
                "\tu.click_product_id=p.product_id limit 100");

        t1DS.createOrReplaceTempView("t1");
        // t1DS.show();

        spark.udf().register("cityMark",udaf(new CityMark(),Encoders.STRING()));

        // 将区域内的产品点击次数统计出来
        Dataset<Row> t2ds = spark.sql("select \n" +
                "\tarea,\n" +
                "\tproduct_name,\n" +
                "\tcityMark(city_name) mark,\n" +
                "\tcount(*) counts\n" +
                "from\t\n" +
                "\tt1\n" +
                "group by\n" +
                "\tarea,product_name");
        // t2ds.show();
        t2ds.createOrReplaceTempView("t2");
        // 对区域内产品点击的次数进行排序  找出区域内的top3
        spark.sql("select\n" +
                "\tarea,\n" +
                "\tproduct_name,\n" +
                "\tmark,\n" +
                "\trank() over (partition by area order by counts desc) rk\n" +
                "from \n" +
                "\tt2").createOrReplaceTempView("t3");
        // 使用过滤  取出区域内的top3
        spark.sql("select\n" +
                "\tarea,\n" +
                "\tproduct_name,\n" +
                "\tmark \n" +
                "from\n" +
                "\tt3\n" +
                "where \n" +
                "\trk < 4").show(50,false);

        spark.close();


    }

    @Data
    public static class Buffer implements Serializable {
        private Long totalCount;
        private HashMap<String,Long> map;

        public Buffer() {
        }

        public Buffer(Long totalCount, HashMap<String, Long> map) {
            this.totalCount = totalCount;
            this.map = map;
        }
    }

    public static class CityMark extends Aggregator<String, Buffer, String> {
        public static class CityCount {
            public String name;
            public Long count;

            public CityCount(String name, Long count) {
                this.name = name;
                this.count = count;
            }
            public CityCount() {
            }
        }
        public static class CompareCityCount implements Comparator<CityCount> {

            /**
             * 默认倒序
             * @param o1
             * @param o2
             * @return
             */
            @Override
            public int compare(CityCount o1, CityCount o2) {
                if (o1.count > o2.count) {
                    return -1;
                } else return o1.count.equals(o2.count) ? 0 : 1;
            }
        }

        @Override
        public Buffer zero() {
            return new Buffer(0L,new HashMap<String,Long>());
        }
        /**
         * 分区内的预聚合
         *
         * @param b map(城市,sum)
         * @param a 当前行表示的城市
         * @return
         */
        @Override
        public Buffer reduce(Buffer b, String a) {
            HashMap<String, Long> hashMap = b.getMap();
            // 如果map中已经有当前城市  次数+1
            // 如果map中没有当前城市    0+1
            hashMap.put(a, hashMap.getOrDefault(a, 0L) + 1);
            b.setTotalCount(b.getTotalCount() + 1L);
            return b;
        }

        /**
         * 合并多个分区间的数据
         *
         * @param b1 (北京,100),(上海,200)
         * @param b2 (天津,100),(上海,200)
         * @return
         */
        @Override
        public Buffer merge(Buffer b1, Buffer b2) {
            b1.setTotalCount(b1.getTotalCount() + b2.getTotalCount());
            HashMap<String, Long> map1 = b1.getMap();
            HashMap<String, Long> map2 = b2.getMap();
            // 将map2中的数据放入合并到map1
            map2.forEach(new BiConsumer<String, Long>() {
                @Override
                public void accept(String s, Long aLong) {
                    map1.put(s, aLong + map1.getOrDefault(s, 0L));
                }
            });
            return b1;
        }
        /**
         * map => {(上海,200),(北京,100),(天津,300)}
         *
         * @param reduction
         * @return
         */
        @Override
        public String finish(Buffer reduction) {
            Long totalCount = reduction.getTotalCount();
            HashMap<String, Long> map = reduction.getMap();
            // 需要对map中的value次数进行排序
            ArrayList<CityCount> cityCounts = new ArrayList<>();

            // 将map中的数据放入到treeMap中 进行排序
            map.forEach(new BiConsumer<String, Long>() {
                @Override
                public void accept(String s, Long aLong) {
                    cityCounts.add(new CityCount(s, aLong));
                }
            });

            cityCounts.sort(new CompareCityCount());
            ArrayList<String> resultMark = new ArrayList<>();

            Double sum = 0.0;

            // 当前没有更多的城市数据  或者  已经找到两个城市数据了  停止循环
            while (!(cityCounts.size() == 0) && resultMark.size() < 2) {
                CityCount cityCount = cityCounts.get(0);
                resultMark.add(cityCount.name + String.format("%.2f",cityCount.count.doubleValue() / totalCount * 100) + "%");
                cityCounts.remove(0);
            }

            // 拼接其他城市
            if (cityCounts.size() > 0) {
                resultMark.add("其他" + String.format("%.2f", 100 - sum) + "%");
            }

            StringBuilder cityMark = new StringBuilder();
            for (String s : resultMark) {
                cityMark.append(s).append(",");
            }

            return cityMark.substring(0, cityMark.length() - 1);
        }

        @Override
        public Encoder<Buffer> bufferEncoder() {
            return Encoders.javaSerialization(Buffer.class);
        }

        @Override
        public Encoder<String> outputEncoder() {
            return Encoders.STRING();
        }
    }

}
