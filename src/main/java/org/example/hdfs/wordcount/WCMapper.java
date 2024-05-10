package org.example.hdfs.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**一、输入数据：
 * KEYIN：输入数据的key的类型,默认的key是文本的偏移量
 * VALUEIN：输入数据的value的类型, 这行文本的内容
 hello tom
 * 二、输出数据
 * KEYOUT：处理后输出的key的类型
 * VALUEOUT：处理后输出的value的类型   1
 */
public class WCMapper extends Mapper<LongWritable, Text,Text, IntWritable> {
    //定义输出的valus的对象
    private static final IntWritable valueOut = new IntWritable(1);
    //定义输出的Key的对象
    private Text keyOut = new Text();
    //文本中的每一行调用一次map方法
    protected void map(LongWritable Key,Text value,Mapper<LongWritable, Text,Text,IntWritable>.Context context) throws IOException, InterruptedException {
        if (value != null) {
            //value就是读取到的当前行中的内容，并将之转换为字符串
            String lineContenct = value.toString();
            //将字符串进行处理：先去掉两端的空格，然后按照空格进行切分
            String[] words = lineContenct.trim().split(" ");
            //遍历数组逐一进行输出
            for (String word :words
                 ) {
                //将word内容封装到ketOut中
                keyOut.set(word);
                context.write(keyOut,valueOut);
            }
        }
    }
}
