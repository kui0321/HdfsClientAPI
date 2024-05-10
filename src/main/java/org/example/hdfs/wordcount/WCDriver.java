package org.example.hdfs.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URISyntaxException;

/**WordCount程序的入口类：
 * 在该类中配置job作业的相关参数、创建job对象、设置输入
 输出路径、提交作业
 */
public class WCDriver {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, URISyntaxException {
        //校验输入参数
        if (args == null || args.length < 2) {
            System.out.println("Usage:hadoop jar xxx.jar com.itbaizhan.WCDriver <inpath> <outpath>");
            System.exit(0);
        }
        //1.创建文件配置对象
        Configuration conf = new Configuration();
        HDFSDeleteFolder hdfsDeleteFolder = new HDFSDeleteFolder();
        hdfsDeleteFolder.HdfsDelete(conf);
        //设置本地运行
        conf.set("mapreduce.framework.name","local");
        //3.创建Job对象
        Job job = Job.getInstance(conf);
        //4.设置关联的Deiver类
        job.setJarByClass(WCDriver.class);
        //设置Mapper相关信息
        job.setMapperClass(WCMapper.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        //8.设置reducer相关信息
        job.setReducerClass(WCReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        //设置输入路径
        FileInputFormat.setInputPaths(job,new Path(args[0]));
        //设置输出路径
        FileOutputFormat.setOutputPath(job,new Path(args[1]));
        //提交作业
        boolean result = job.waitForCompletion(true);
        System.out.println(result?0:1);
    }
}
