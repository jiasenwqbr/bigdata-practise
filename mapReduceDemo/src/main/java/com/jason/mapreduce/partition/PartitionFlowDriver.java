package com.jason.mapreduce.partition;


import com.jason.mapreduce.writable.FlowBean;
import com.jason.mapreduce.writable.FlowMapper;
import com.jason.mapreduce.writable.FlowReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class PartitionFlowDriver {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

        //1 获取job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        //2 关联本Driver类
        job.setJarByClass(PartitionFlowDriver.class);

        //3 关联Mapper和Reducer
        job.setMapperClass(FlowMapper.class);
        job.setReducerClass(FlowReducer.class);

        //4 设置Map端输出数据的KV类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);

        //5 设置程序最终输出的KV类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        //6 指定自定义分区器
        job.setPartitionerClass(ProvincePartitioner.class);

        // 7 同时指定相应数量的ReduceTask
        job.setNumReduceTasks(5);

        //8 设置输入输出路径
        FileInputFormat.setInputPaths(job, new Path("D:\\data\\partition\\input"));
        FileOutputFormat.setOutputPath(job, new Path("D:\\data\\partition\\output5"));

        //9 提交Job
        boolean b = job.waitForCompletion(true);
        System.exit(b ? 0 : 1);


    }
}
