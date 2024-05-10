package org.example.hdfs.weathercount;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.zookeeper.Watcher;

public class WeatherGroupingComparator extends WritableComparator {
    public WeatherGroupingComparator(){
        //让当前类的父类WritableComparator创建指定类型Weather类的对象，否则会出现空指针异常
        super(Weather.class,true);
    }

    public int compare(WritableComparable a, WritableComparable b) {
        //强制类型转换
        Weather wa = (Weather)a;
        Weather wb = (Weather)b;
        int result = wa.getYear().compareTo(wb.getYear());
        if (result == 0){
            result = wa.getMonth().compareTo(wb.getMonth());
        }
        //先比较年
        return result;
    }
}
