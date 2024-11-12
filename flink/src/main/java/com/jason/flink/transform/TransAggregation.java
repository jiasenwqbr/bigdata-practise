package com.jason.flink.transform;

import com.jason.flink.source.WaterSensor;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年11月12日 11:04
 */
public class TransAggregation {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<WaterSensor> stream = env.fromElements(
                new WaterSensor("sensor_1", 1L, 1),
                new WaterSensor("sensor_1", 2L, 2),
                new WaterSensor("sensor_2", 2L, 2),
                new WaterSensor("sensor_2", 8L, 8),
                new WaterSensor("sensor_3", 3L, 3),
                new WaterSensor("sensor_3", 9L, 9)
        );

        SingleOutputStreamOperator<WaterSensor> vc = stream.keyBy(e -> e.id).max("vc");// 指定字段名称
        vc.print();

        env.execute();
    }
}
