package com.jason.flink.transform;

import com.jason.flink.source.WaterSensor;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年11月13日 11:14
 */
public class TransFunctionUDF {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<WaterSensor> stream = env.fromElements(
                new WaterSensor("sensor_1", 1L, 1),
                new WaterSensor("sensor_1", 2L, 2),
                new WaterSensor("sensor_2", 2L, 2),
                new WaterSensor("sensor_3", 3L, 3)
        );
        SingleOutputStreamOperator<WaterSensor> filter = stream.filter(new UserFilter());
        filter.print();
        env.execute();


    }

    public static class UserFilter implements FilterFunction<WaterSensor> {

        @Override
        public boolean filter(WaterSensor waterSensor) throws Exception {
            return waterSensor.getId().equals("sensor_1");
        }
    }
}