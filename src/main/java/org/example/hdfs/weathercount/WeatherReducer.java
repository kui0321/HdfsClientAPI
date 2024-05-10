package org.example.hdfs.weathercount;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class WeatherReducer extends Reducer<Weather, Text,Text, NullWritable> {
    protected void reduce(Weather key, Iterable<Text> values, Reducer<Weather, Text, Text, NullWritable>.Context context) throws IOException, InterruptedException {
        //定义一个变量
        int day = -1;
        //遍历values
        for (Text value:values)
            if (day == -1) {
                //说明这是当前年月下的温度最高的第一条数据，将之直接输出
                context.write(value, NullWritable.get());
                //将当前条数据的天获取出来赋值给day变量
                day = key.getDay();
            }else {
                if (day != key.getDay()){
                    //当前条数据的天不等于上一次输出的天时，才会输出。
                    context.write(value,NullWritable.get());
                    //已经输出了当前年月下温度最高的两天数据，该组数据结束。
                    break;
                }
            }
        }
    }
