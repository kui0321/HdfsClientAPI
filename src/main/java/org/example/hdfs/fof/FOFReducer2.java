package org.example.hdfs.fof;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.*;

public class FOFReducer2 extends Reducer<Text,Text,Text,Text> {
    private Text outValue = new Text();

    private Map<String, Integer> friends = new HashMap<>();

    private List<Map.Entry<String, Integer>> list =  new ArrayList<>();

    protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context) throws IOException, InterruptedException {
        //清空集合中的数据避免之前数据的干扰
        friends.clear();
        list.clear();

        for (Text value: values) {
            String[] nameNum = value.toString().split(",");
            friends.put(nameNum[0], Integer.parseInt(nameNum[1]));
        }
         //直接添加到list中
        for (Map.Entry<String, Integer> entry: friends.entrySet()
             ) {
            list.add(entry);
        }
        //然后再对list中的数据进行排序
        list.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getValue()- o2.getValue();
            }
        });

        String result  = "";
        ////获取推荐的间接好友：top2
        for (int i = 0; i<(list.size()>=2?2: list.size());i++) {
            result += list.get(i).getKey()+",";
        }
        //去掉最后一个逗号
        result = result.substring(0,result.length()-1);
        //将result封装到outValue中
        outValue.set(result);
        //输出
        context.write(key,outValue);
    }

}
