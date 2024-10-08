package com.jason.mapreduce.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;


public class WordCountReducer extends Reducer<Text, IntWritable,Text,IntWritable> {
    int sum;
    IntWritable v = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
        // 1 累加求和
        sum = 0;
        for (IntWritable value:values){
            sum+=value.get();
        }
        // 2 输出
        v.set(sum);
        context.write(key,v);
    }
}
