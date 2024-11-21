package com.jason.flink.transform;

import org.apache.flink.streaming.api.datastream.ConnectedStreams;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoMapFunction;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年11月21日 13:47
 */
public class ConnectDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStreamSource<Integer> source1 = env.fromElements(1, 2, 3);
        DataStreamSource<String> source2 = env.fromElements("a", "b", "c");

        ConnectedStreams<Integer, String> connect = source1.connect(source2);
        SingleOutputStreamOperator<String> result = connect.map(new CoMapFunction<Integer, String, String>() {
            @Override
            public String map1(Integer value) throws Exception {
                return "来源于数字流:" + value.toString();
            }

            @Override
            public String map2(String value) throws Exception {
                return "来源于字母流:" + value;
            }
        });
        result.print();



        env.execute();
    }



}
