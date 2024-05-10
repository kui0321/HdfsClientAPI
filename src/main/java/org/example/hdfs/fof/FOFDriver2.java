package org.example.hdfs.fof;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class FOFDriver2 {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        if (args == null || args.length != 2) {
            System.out.println("Usage:yarn jar fof.jar com.itbaizhan.FOFDriver2 <inputPath> <outputPath>");
            System.exit(1);
        }
        //创建配置文件对象，加载默认的配置
        Configuration configuration = new Configuration(true);
        //设置本地运行
        configuration.set("mapreduce.framework.name","local");
        Job job = Job.getInstance(configuration,"好友推荐RM2");

        job.setJarByClass(FOFDriver2.class);
        //设置InputFormatClass 将当前行文本中第一个\t之前的内容作为key，之后的内容作为value
        job.setInputFormatClass(KeyValueTextInputFormat.class);
        job.setMapperClass(FOFMapper2.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        //6.设置自定义Reducer类和它输出的key和value类型
        job.setReducerClass(FOFReducer2.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));

        Path outputPath = new Path(args[1]);

        FileSystem fileSystem = FileSystem.get(configuration);

        if (fileSystem.exists(outputPath)) {
            fileSystem.delete(outputPath,true);
        }

        FileOutputFormat.setOutputPath(job, outputPath);
        job.waitForCompletion(true);
    }
}
