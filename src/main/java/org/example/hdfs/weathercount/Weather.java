package org.example.hdfs.weathercount;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Weather implements WritableComparable<Weather> {
    //定义属性
    //定义属性
    private Integer Year;
    private Integer Month;
    private Integer Day;
    private Double Temperature;

    //该方法可以在排序比较器中调用，也可以在分组比较器中调用。
    //但是排序比较和分组比较器逻辑是不同，所以只能满足其中一方调用。想被谁调用，就按照对应的逻辑编写
    public int compareTo(Weather o) {
        //逻辑按照排序比较器实现
        int result  = Year.compareTo(o.getYear());
        if (result == 0) {
            result = Month.compareTo(o.getMonth());
            if (result == 0) {
                 result = Temperature.compareTo(o.getTemperature());
            }

        }
        return result;
    }

    //通过内部类注册key自带比较器
    public static class Comparator extends WritableComparator{
        public Comparator(){
            super(Weather.class);
        }

        public int compare(WritableComparable a,WritableComparable b){
            Weather wa = (Weather) a;
            Weather wb = (Weather) b;
            //如果Weather类中的compareTo方法的比较逻辑符合此处的逻辑的话，可以直接调用
            return wa.compareTo(wb);
        }
    }

    static {
        WritableComparator.define(Weather.class, new Weather.Comparator());
    }

    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(Year);
        dataOutput.writeInt(Month);
        dataOutput.writeInt(Day);
        dataOutput.writeDouble(Temperature);
    }

    public void readFields(DataInput dataInput) throws IOException {
        Year = dataInput.readInt();
        Month = dataInput.readInt();
        Day = dataInput.readInt();
        Temperature=dataInput.readDouble();
    }

    @Override
    public String toString() {
        return "Weather{" +
                "Year=" + Year +
                ", Month=" + Month +
                ", Day=" + Day +
                ", Temperature=" + Temperature +
                '}';
    }

    public Integer getYear() {
        return Year;
    }

    public void setYear(Integer year) {
        Year = year;
    }

    public Integer getMonth() {
        return Month;
    }

    public void setMonth(Integer month) {
        Month = month;
    }

    public Integer getDay() {
        return Day;
    }

    public void setDay(Integer day) {
        Day = day;
    }

    public Double getTemperature() {
        return Temperature;
    }

    public void setTemperature(Double temperature) {
        Temperature = temperature;
    }
}
