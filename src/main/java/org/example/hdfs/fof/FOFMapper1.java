package org.example.hdfs.fof;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FOFMapper1 extends Mapper<LongWritable, Text,Text, IntWritable> {
    //定义输出的key的对象
    private Text outkey = new Text();
    //定义输出value对象
    private IntWritable outvalue0 = new IntWritable(0);
    private IntWritable outvalue1 = new IntWritable(1);

    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {
        //按照空格进行拆分
        String[] names = value.toString().trim().split(" ");
        //先处理直接关系 tom 和后续的名称直接好
        for (int i = 1; i<names.length; i++){
            //names[0] 和 names[i] 包正无论是A和B还是B和A，都拼接为A:B
            outkey.set(getFofName(names[0],names[i]));
            context.write(outkey,outvalue0);
            //再处理间接好友 hello->hadoop cathadoop->cat
            for(int j = i+1;j<names.length;j++){
                outkey.set(getFofName(names[i],names[j]));
                context.write(outkey,outvalue1);
            }
        }

    }



    private String getFofName(String name1,String name2){
        return  name1.compareTo(name2)<0?name1+":"+name2:name2+":"+name1;
    }
}
