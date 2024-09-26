package com.jason.mapreduce.combinewordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class CombineWordCountReducer extends Reducer<Text, LongWritable,Text,LongWritable> {
    int sum;
    LongWritable v = new LongWritable();
    @Override
    protected void reduce(Text key, Iterable<LongWritable> values, Reducer<Text, LongWritable, Text, LongWritable>.Context context) throws IOException, InterruptedException {
        for (LongWritable num : values){
            sum += Long.valueOf(v.get());
        }
        v.set(sum);
        context.write(key,v);
    }
}
