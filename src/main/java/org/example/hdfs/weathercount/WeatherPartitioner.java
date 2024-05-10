package org.example.hdfs.weathercount;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

//泛型中的类型分别 Mapper输出的key和value的类型
public class WeatherPartitioner extends Partitioner<Weather, Text> {
    public int getPartition(Weather weather, Text text, int numPartitions) {
        return weather.getMonth()%numPartitions;
    }
}
