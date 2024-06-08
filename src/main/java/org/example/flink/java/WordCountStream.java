package org.example.flink.java;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * WordCount流处理程序的实现类。
 * 该程序演示了如何使用Apache Flink进行简单的单词计数流处理。
 */
public class WordCountStream {
    /**
     * 程序的入口点。
     * @param args 命令行参数，预期使用节点地址和端口作为输入源。
     * @throws Exception 如果执行环境设置或操作失败。
     */
    public static void main(String[] args) throws Exception {
        // 创建流执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 设置并行度为1，确保程序顺序执行
        env.setParallelism(1);

        // 从指定的socket读取文本流
        DataStreamSource<String> dataSource = env.socketTextStream("node3",8888);

        // 对文本流进行分词，每个单词作为一个单独的元素
        SingleOutputStreamOperator<String> words = dataSource.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String s, Collector<String> collector) throws Exception {
                String [] words = s.split("\\s+");
                for (String word: words) {
                    collector.collect(word);
                }
            }
        });

        // 将每个单词与计数1关联，准备进行聚合操作
        SingleOutputStreamOperator<Tuple2<String, Integer>> tupSteram = words.map(new MapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> map(String s) throws Exception {
                return Tuple2.of(s, 1);
            }
        });

        // 根据单词进行分组，为单词计数做准备
        KeyedStream<Tuple2<String, Integer>, String> keyedStream = tupSteram.keyBy(tup -> tup.f0);

        // 计算每个单词出现的总次数
        SingleOutputStreamOperator<Tuple2<String, Integer>> sum = keyedStream.sum(1);

        // 将结果打印到控制台
        sum.print();

        // 执行流处理作业
        env.execute("WordCountStream");
    }
}
