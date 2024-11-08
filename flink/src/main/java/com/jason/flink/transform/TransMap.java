package com.jason.flink.transform;

import com.jason.flink.source.WaterSensor;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年11月08日 17:54
 */
public class TransMap {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<WaterSensor> stream = env.fromElements(
                new WaterSensor("sensor_1", 1L, 1),
                new WaterSensor("sensor_2", 2L, 2)
        );
        // 方式一：传入匿名类，实现MapFunction
        SingleOutputStreamOperator<String> map = stream.map(new MapFunction<WaterSensor, String>() {
            @Override
            public String map(WaterSensor waterSensor) throws Exception {
                return waterSensor.toString();
            }
        });
        map.print();

        SingleOutputStreamOperator<String> map1 = stream.map(water -> water.toString());
        map1.print();
        // 方式二：传入MapFunction的实现类
        // stream.map(new UserMap()).print();

        SingleOutputStreamOperator<String> map2 = stream.map(new UserMap());
        map2.print();


        env.execute();



    }
    public static class UserMap implements MapFunction<WaterSensor, String> {
        @Override
        public String map(WaterSensor e) throws Exception {
            return e.id;
        }
    }
}
