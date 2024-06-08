package org.example.flink.java;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.*;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;


/**
 * WordCount批量处理示例类。
 * 本类演示了如何使用Flink进行简单的词频统计。
 */
public class WordCountBatch {
    /**
     * 程序入口。
     * @param args 命令行参数，预期包含输入文件路径。
     * @throws Exception 如果程序执行过程中出现异常。
     */
    public static void main(String[] args) throws Exception {
        // 创建执行环境
        //1.构建执行环境-env
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        // 从指定路径读取文本文件作为数据源
        DataSource<String> dataSource = env.readTextFile("D:\\BIgdate\\bigdateinfo\\Flink\\软件\\datas\\words");
        // 对文本内容进行分割，提取每个单词
        //3.1分割单词
        FlatMapOperator<String, String> word = dataSource.flatMap(new FlatMapFunction<String, String>(){
            @Override
            public void flatMap(String line, Collector<String> collector) throws Exception {
                // 去除行两端的空白字符并按空格分割
                String[] words = line.trim().split("\\s+");
                // 将每个单词输出
                for (String word:
                        words) {
                    collector.collect(word);
                }
            }
        });
        // 将单词转换为二元组形式，每个单词与出现次数1组成
        //将单词转化为二元组 hello ->(hello,1)
        MapOperator<String, Tuple2<String, Integer>> tupMap = word.map(new MapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> map(String word) throws Exception {
                // 返回单词和1组成的二元组
                return Tuple2.of(word,1);
            }
        });
        // 按单词进行分组，准备进行求和操作
        //将相同的key分为一组   0表示二元组中第一个
        UnsortedGrouping<Tuple2<String,Integer>> tupGroup = tupMap.groupBy(0); //
        // 对每个单词的出现次数进行求和
        //分组进行求和
        AggregateOperator<Tuple2<String,Integer>> resultsum = tupGroup.sum(1);
        // 输出统计结果
        //数据接收器-sink
        resultsum.print();
    }
}

