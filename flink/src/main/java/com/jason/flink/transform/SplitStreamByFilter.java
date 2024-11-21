package com.jason.flink.transform;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年11月21日 10:21
 */
public class SplitStreamByFilter {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //env.setParallelism(2);

        DataStreamSource<String> stream = env.socketTextStream("127.0.0.1", 7777);
        SingleOutputStreamOperator<Integer> ds = stream.map(Integer::valueOf);
        SingleOutputStreamOperator<Integer> ds1 = ds.filter(x -> x % 2 == 1);
        SingleOutputStreamOperator<Integer> ds2 = ds.filter(x -> x % 2 == 0);
        ds1.print("奇数");
        ds2.print("偶数");

        env.execute();

    }
}
