package com.jason.flink.transform;

import com.jason.flink.source.WaterSensor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description:
 * @author: 贾森
 * @date: 2024年11月09日 11:33
 */
public class TransMap1 {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<WaterSensor> stream = env.fromElements(
                new WaterSensor("sensor_1", 1L, 1),
                new WaterSensor("sensor_2", 2L, 2)
        );
        SingleOutputStreamOperator<Map<String, WaterSensor>> map = stream.map(water -> {
            Map<String, WaterSensor> m = new LinkedHashMap<>();
            m.put(water.getId(), water);
            return m;
        }).returns(new TypeHint<Map<String, WaterSensor>>() {});
        map.print();

        env.execute();
    }
}




/*
* 以下代码运行为什么报错？
```java
public class TransMap1 {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<WaterSensor> stream = env.fromElements(
                new WaterSensor("sensor_1", 1L, 1),
                new WaterSensor("sensor_2", 2L, 2)
        );
        SingleOutputStreamOperator<Map<String, WaterSensor>> map = stream.map(water -> {
            Map<String, WaterSensor> m = new LinkedHashMap<>();
            m.put(water.getId(), water);
            return m;
        });
        map.print();

        env.execute();
    }
}
```
报错信息如下：
Exception in thread "main" org.apache.flink.api.common.functions.InvalidTypesException: The return type of function 'main(TransMap1.java:23)' could not be determined automatically, due to type erasure. You can give type information hints by using the returns(...) method on the result of the transformation call, or by letting your function implement the 'ResultTypeQueryable' interface.
	at org.apache.flink.api.dag.Transformation.getOutputType(Transformation.java:543)
	at org.apache.flink.streaming.api.datastream.DataStream.addSink(DataStream.java:1237)
	at org.apache.flink.streaming.api.datastream.DataStream.print(DataStream.java:937)
	at com.jason.flink.transform.TransMap1.main(TransMap1.java:28)
Caused by: org.apache.flink.api.common.functions.InvalidTypesException: The generic type parameters of 'Map' are missing. In many cases lambda methods don't provide enough information for automatic type extraction when Java generics are involved. An easy workaround is to use an (anonymous) class instead that implements the 'org.apache.flink.api.common.functions.MapFunction' interface. Otherwise the type has to be specified explicitly using type information.
	at org.apache.flink.api.java.typeutils.TypeExtractionUtils.validateLambdaType(TypeExtractionUtils.java:371)
	at org.apache.flink.api.java.typeutils.TypeExtractor.getUnaryOperatorReturnType(TypeExtractor.java:568)
	at org.apache.flink.api.java.typeutils.TypeExtractor.getMapReturnTypes(TypeExtractor.java:154)
	at org.apache.flink.streaming.api.datastream.DataStream.map(DataStream.java:575)
	at com.jason.flink.transform.TransMap1.main(TransMap1.java:23)
* */


/*
*
* 这个错误的根本原因是 Flink 无法自动推断 Lambda 表达式中返回类型的泛型信息。这是因为 Java 的泛型类型在编译时被擦除（type erasure），导致 Flink 无法确定 Map<String, WaterSensor> 的具体类型。

Flink 给出了两种解决方案：

使用 returns(...) 方法明确指定返回类型。
不使用 Lambda 表达式，而是定义一个匿名类，显式地实现 MapFunction 接口，从而提供类型信息。
*解决方案 1：使用 returns 方法
在 map 操作后调用 returns(...) 方法来指定类型：
* SingleOutputStreamOperator<Map<String, WaterSensor>> map = stream.map(water -> {
    Map<String, WaterSensor> m = new LinkedHashMap<>();
    m.put(water.getId(), water);
    return m;
}).returns(new TypeHint<Map<String, WaterSensor>>() {});
这里的 new TypeHint<Map<String, WaterSensor>>() {} 提供了一个类型提示，Flink 将能够自动推断出泛型信息
*
*
*解决方案 2：使用匿名类代替 Lambda 表达式
用匿名类替换 Lambda 表达式实现 MapFunction 接口，这样可以显式提供返回类型：
* SingleOutputStreamOperator<Map<String, WaterSensor>> map = stream.map(new MapFunction<WaterSensor, Map<String, WaterSensor>>() {
    @Override
    public Map<String, WaterSensor> map(WaterSensor water) throws Exception {
        Map<String, WaterSensor> m = new LinkedHashMap<>();
        m.put(water.getId(), water);
        return m;
    }
});
在这个版本中，由于明确地实现了 MapFunction<WaterSensor, Map<String, WaterSensor>>，Flink 可以从接口中推断出返回类型。

这两种方法都可以解决 InvalidTypesException 的错误。
* */