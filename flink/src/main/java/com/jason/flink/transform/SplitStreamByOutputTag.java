package com.jason.flink.transform;

import com.jason.flink.source.WaterSensor;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.datastream.SideOutputDataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年11月21日 10:58
 */
public class SplitStreamByOutputTag {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //env.setParallelism(2);

        SingleOutputStreamOperator<WaterSensor> ds = env.socketTextStream("127.0.0.1", 7777)
                .map(new WaterSensorMapFunction());

        OutputTag<WaterSensor> s1 = new OutputTag<WaterSensor>("s1", Types.POJO(WaterSensor.class)){};
        OutputTag<WaterSensor> s2 = new OutputTag<WaterSensor>("s2", Types.POJO(WaterSensor.class)){};

        //返回的都是主流
        SingleOutputStreamOperator<WaterSensor> ds1 = ds.process(new ProcessFunction<WaterSensor, WaterSensor>() {
            @Override
            public void processElement(WaterSensor waterSensor,
                                       ProcessFunction<WaterSensor, WaterSensor>.Context context,
                                       Collector<WaterSensor> collector) throws Exception {
                if ("s1".equals(waterSensor.getId())) {
                    context.output(s1, waterSensor);
                } else if ("s2".equals(waterSensor.getId())) {
                    context.output(s2, waterSensor);
                } else {
                    // 主流
                    collector.collect(waterSensor);
                }
            }
        });
        ds1.print("主流，非S1,S2的传感器");
        SideOutputDataStream<WaterSensor> sideOutputS1 = ds1.getSideOutput(s1);
        SideOutputDataStream<WaterSensor> sideOutputS2 = ds1.getSideOutput(s2);
        sideOutputS1.printToErr("S1");
        sideOutputS2.printToErr("S2");

        env.execute();


    }
    static class WaterSensorMapFunction implements MapFunction<String,WaterSensor> {
        @Override
        public WaterSensor map(String value) throws Exception {
            System.out.println("--------------"+value);
            String[] datas = value.split(",");
            System.out.println(datas.length);
            return new WaterSensor(datas[0],Long.valueOf(datas[1]) ,Integer.valueOf(datas[2]) );
        }
    }
}
