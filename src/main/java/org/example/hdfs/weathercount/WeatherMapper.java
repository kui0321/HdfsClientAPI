package org.example.hdfs.weathercount;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**泛型的类型：
 * 输入的key、value的类型
 * LongWritable：偏移量
 * Text：当前行文本的内容输出的key、value的类型
 * Weather：将当前行的数据拆分后封装到Weather类的对象中
 * Text：当前行文本的内容
 */
public class WeatherMapper extends Mapper<LongWritable, Text,Weather, Text> {
    //定义输出的key的对象
    private Weather weather = new Weather();
    //在map方法外定义对象的好处是一个MapTask只需要实例化一次
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Calendar calendar= Calendar.getInstance();
    //覆写父类中的map方法


    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Weather, Text>.Context context) throws IOException, InterruptedException {
        //2020-10-01 12:21:02 37c

        //将接收到的数据转换为字符串，并去掉两端的空格
        String line = value.toString().trim();
        //将日期和文档拆分开
        String[] datas = line.split("\t");
        //将温度处理,将温度后面的C去掉
        String temperatureStr = datas[1].substring(0, datas[1].length()-1);
        //类型转换
        Double temperatureDouble = Double.parseDouble(temperatureStr);
        //将温度封装到Water对象中
        weather.setTemperature(temperatureDouble);
        //处理日期
        try {
            Date date = simpleDateFormat.parse(datas[0]);
            //从date对象获取年月日，并分别封装到weatar对象中
            calendar.setTime(date);
            weather.setYear(calendar.get(Calendar.YEAR));
            weather.setMonth(calendar.get(Calendar.MONTH)+1);
            weather.setDay(calendar.get(Calendar.DAY_OF_MONTH));
            context.write(weather,value);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
