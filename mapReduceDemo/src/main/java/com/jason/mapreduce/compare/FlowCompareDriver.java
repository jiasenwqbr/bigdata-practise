package com.jason.mapreduce.compare;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class FlowCompareDriver {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        //1 获取job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        //2 关联本Driver类
        job.setJarByClass(FlowCompareDriver.class);

        //3 关联Mapper和Reducer
        job.setMapperClass(FlowCompareMapper.class);
        job.setReducerClass(FlowCompareReducer.class);

        //4 设置Map端输出数据的KV类型
        job.setMapOutputKeyClass(FlowCompareBean.class);
        job.setMapOutputValueClass(Text.class);

        //5 设置程序最终输出的KV类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowCompareBean.class);

        // 设置自定义分区器
        job.setPartitionerClass(ProvincePartitioner2.class);

        // 设置对应的ReduceTask的个数
        job.setNumReduceTasks(5);

        //6 设置输入输出路径
        FileInputFormat.setInputPaths(job, new Path("D:\\data\\compare\\input"));
        FileOutputFormat.setOutputPath(job, new Path("D:\\data\\compare\\output"));

        //7 提交Job
        boolean b = job.waitForCompletion(true);
        System.exit(b ? 0 : 1);
    }
}
