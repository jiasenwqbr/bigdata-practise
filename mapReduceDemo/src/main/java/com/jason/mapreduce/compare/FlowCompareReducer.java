package com.jason.mapreduce.compare;

import com.jason.mapreduce.writable.FlowBean;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class FlowCompareReducer  extends Reducer<FlowCompareBean, Text, Text, FlowCompareBean> {
    @Override
    protected void reduce(FlowCompareBean key, Iterable<Text> values, Reducer<FlowCompareBean, Text, Text, FlowCompareBean>.Context context) throws IOException, InterruptedException {
        //遍历values集合,循环写出,避免总流量相同的情况
        for (Text value : values) {
            //调换KV位置,反向写出
            context.write(value,key);
        }
    }
}
