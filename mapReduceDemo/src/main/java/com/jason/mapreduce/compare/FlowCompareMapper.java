package com.jason.mapreduce.compare;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FlowCompareMapper extends Mapper<LongWritable, Text,FlowCompareBean,Text> {
    private FlowCompareBean outK = new FlowCompareBean();
    private Text outV = new Text();
    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, FlowCompareBean, Text>.Context context) throws IOException, InterruptedException {
        //1 获取一行数据
        String line = value.toString();

        //2 按照"\t",切割数据
        String[] split = line.split("\t");

        //3 封装outK outV
        outK.setUpFlow(Long.parseLong(split[1]));
        outK.setDownFlow(Long.parseLong(split[2]));
        outK.setSumFlow();
        outV.set(split[0]);

        //4 写出outK outV
        context.write(outK,outV);
    }
}
