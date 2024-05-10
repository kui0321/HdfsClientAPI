package org.example.hdfs.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**输入的kv对的类型：KEYIN, VALUEIN, （分别和Mapper
 输出的kv对的类型保持一致）
 * 输出的kv对的类型：KEYOUT, VALUEOUT
 */

public class WCReducer extends Reducer<Text, IntWritable,Text,IntWritable> {
    private int sum;
    private IntWritable valOut= new IntWritable();
    //相同的key为一组，调用一次reduce方法
    protected void reduce(Text Key,Iterable<IntWritable> values,Reducer<Text,IntWritable,Text,IntWritable>.Context context) throws IOException, InterruptedException {
        //首先将sum重置为零，避免上一组数据值的干扰
        sum = 0;
        //遍历values,累加求和
        for (IntWritable value: values
             ) {
            sum += value.get();
        }
        //将计算你的结果sum封装在valOut中
        valOut.set(sum);
        context.write(Key,valOut);

    }
}
