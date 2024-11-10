package com.jason.flink.transform;

import com.jason.flink.source.WaterSensor;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年11月10日 19:08
 */
public class TransFlatmap {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<WaterSensor> stream = env.fromElements(

                new WaterSensor("sensor_1", 1L, 1),
                new WaterSensor("sensor_1", 2L, 2),
                new WaterSensor("sensor_2", 2L, 2),
                new WaterSensor("sensor_3", 3L, 3)

        );

        stream.flatMap(new MyFlatMap()).print();

        env.execute();

    }

    public static class MyFlatMap implements FlatMapFunction<WaterSensor, String> {

        @Override
        public void flatMap(WaterSensor value, Collector<String> out) throws Exception {

            if (value.id.equals("sensor_1")) {
                out.collect(String.valueOf(value.vc));
            } else if (value.id.equals("sensor_2")) {
                out.collect(String.valueOf(value.ts));
                out.collect(String.valueOf(value.vc));
            }
        }
    }
}
