package org.example.hdfs.weathercount;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class WeatherSortComparator extends WritableComparator {
    public WeatherSortComparator(){
        //让当前类的父类为我们创建key对应类Weather类的对象
        super(Weather.class,true);

    }

    public int compare(WritableComparable a, WritableComparable b){
        //进行强制类型转换
        Weather wa = (Weather)a;
        Weather wb = (Weather)b;
        //如果Weather类中的compareTo方法的比较逻辑符合排序的逻辑可以直接调用
        //return wa.compareTo(wb);
        //如果Weather类中的compareTo方法的比较逻辑不符合排序的逻辑需要重新编写比较的逻辑
        int result = wa.getYear().compareTo(wb.getYear());
        if(result==0){//年相同在比较月份
            result = wa.getMonth().compareTo(wb.getMonth());
            if(result==0){//月相同在比较温度，温度从高到低
                result = wb.getTemperature().compareTo(wa.getTemperature());
            }
        }
        //先比较年
        return result;
    }

}
