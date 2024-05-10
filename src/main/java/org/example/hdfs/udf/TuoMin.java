package org.example.hdfs.udf;

//import org.apache.hadoop.hive.ql.exec.UDF;
//import org.apache.hadoop.io.Text;
//
//public class TuoMin extends UDF {
//    public Text evaluate(final Text tt){
//        if (tt == null) {
//            return null;
//        }
//        //获取手机号码
//        String phone = tt.toString();
//        //脱敏处理
//        String result = phone.substring(0,3)+"****"+phone.substring(7);
//        return new Text(result);
//
//
//    }
//}
