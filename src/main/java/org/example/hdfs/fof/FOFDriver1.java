package org.example.hdfs.fof;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class FOFDriver1 {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        //1.判断输入的参数是否合法
        if (args == null || args.length != 2) {
            System.out.println("Usage:yarn jar com.itbaizhan.FOFDriver1 <inputPath> <outputPath>");
            System.exit(1);
        }


        //创建配置文件对象，加载默认的配置
        Configuration configuration = new Configuration(true);

        //设置本地运行
        configuration.set("mapreduce.framework.name","local");
        //4.创建job对象，并设置job作业的名称
        /*Job job =Job.getInstance(configuration);
        //指定job作业的名称job.setJobName("好友推荐-MR1");*/
        Job job = Job.getInstance(configuration,"好友推荐-MR1");
        job.setJarByClass(FOFDriver1.class);
        //5.设置自定义Mapper类以及输出的key和value的类型
        job.setMapperClass(FOFMapper1.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        //6.设置自定义Reducer类和它输出的key和value类型
         job.setReducerClass(FOFRedcuer1.class);
         job.setOutputKeyClass(Text.class);
         job.setOutputValueClass(IntWritable.class);
        //7.设置输入路径
        FileInputFormat.addInputPath(job,new Path(args[0]));
        //8.设置输出路径
        Path outputPath = new Path(args[1]);
        FileSystem fileSystem = outputPath.getFileSystem(configuration);
        if (fileSystem.exists(outputPath)) {
            fileSystem.delete(outputPath,true);
        }
        FileOutputFormat.setOutputPath(job,outputPath);
        //9.提交作业
        job.waitForCompletion(true);
    }
}
