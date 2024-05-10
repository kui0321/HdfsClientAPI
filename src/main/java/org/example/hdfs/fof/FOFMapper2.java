package org.example.hdfs.fof;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FOFMapper2 extends Mapper<Text,Text,Text,Text> {
    private Text outKey = new Text();
    private Text outValue = new Text();

    protected void map(Text key,Text value,Mapper<Text, Text, Text, Text>.Context context) throws IOException, InterruptedException {
        String[] names = key.toString().split(":");
        String num = value.toString();

        outKey.set(names[0]);
        outValue.set(names[1]+","+num);

        context.write(outKey, outValue);

        outKey.set(names[1]);
        outValue.set(names[0]+","+num);
        context.write(outKey, outValue);
    }
}
