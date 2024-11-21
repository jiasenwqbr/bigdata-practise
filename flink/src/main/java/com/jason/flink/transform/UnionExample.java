package com.jason.flink.transform;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年11月21日 13:41
 */
public class UnionExample {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        DataStreamSource<Integer> ds1 = env.fromElements(1, 2, 3);
        DataStreamSource<Integer> ds2 = env.fromElements(2, 3, 4);
        DataStreamSource<String> ds3 = env.fromElements("5", "5", "6");

        ds1.union(ds2,ds3.map(v->Integer.valueOf(v))).print();
        env.execute();

    }
}
