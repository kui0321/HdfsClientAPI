package org.example.hdfs.fof;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class FOFRedcuer1 extends Reducer<Text, IntWritable,Text,IntWritable> {
    //定义输出的value对象
    private IntWritable outvalue = new IntWritable();
    protected void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
        //定义变量sum表示求和后的值
        int sum = 0;
        ////定义变量表示是否存现过0
        boolean isZero = false;
        //计算sum
        for (IntWritable value:values) {
            if (value.get() == 0){
                isZero = true;
                break;
            }
            sum += value.get();
        }
        //将sum的值封装到outValue中
        outvalue.set(sum);
        ////没有出现过0，才输出。出现过0的话表示直接好友，过滤掉
        if (!isZero){
            context.write(key,outvalue);
        }
    }
}
