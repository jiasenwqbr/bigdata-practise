package com.jason.flink.transform;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年11月21日 09:51
 */

/*nc -lk 7777*/
public class ShuffleExample {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.setParallelism(3);

        DataStreamSource<String> stream = env.socketTextStream("127.0.0.1", 7777);;

        stream.shuffle().print();

        env.execute();
    }
}

/*
* 最简单的重分区方式就是直接“洗牌”。通过调用DataStream的.shuffle()方法，将数据随机地分配到下游算子的并行任务中去。
随机分区服从均匀分布（uniform distribution），所以可以把流中的数据随机打乱，均匀地传递到下游任务分区。
* 因为是完全随机的，所以对于同样的输入数据, 每次执行得到的结果也不会相同。
* 经过随机分区之后，得到的依然是一个DataStream。
* */