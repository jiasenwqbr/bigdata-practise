package com.jason.mapreduce.combinewordcount;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class CombineWordCountMapper extends Mapper<LongWritable, Text,Text,LongWritable> {
    Text k = new Text();
    LongWritable v = new LongWritable(1);
    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable,Text , Text, LongWritable>.Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] arr = line.split(" ");
        for (String word : arr){

            k.set(word);
            context.write(k,v);
        }
    }
}
