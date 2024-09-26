package com.jason.mapreduce.combinewordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class CombineWordCountDriver {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        // 1 获取配置信息以及获取job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        // 如果不设置InputFormat，它默认用的是TextInputFormat.class
        job.setInputFormatClass(CombineTextInputFormat.class);
        //虚拟存储切片最大值设置4m
        CombineTextInputFormat.setMaxInputSplitSize(job, 4194304);

        // 2 关联本Driver程序的jar
        job.setJarByClass(CombineWordCountDriver.class);

        // 3 关联Mapper和Reducer的jar
        job.setMapperClass(CombineWordCountMapper.class);
        job.setReducerClass(CombineWordCountReducer.class);

        // 4 设置Mapper输出的kv类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);

        // 5 设置最终输出kv类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        // 6 设置输入和输出路径
        FileInputFormat.setInputPaths(job, new Path("D:\\data\\combine\\input"));
        FileOutputFormat.setOutputPath(job, new Path("D:\\data\\combine\\output"));

        // 7 提交job
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}
