package org.example.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerAutoOffset {
    public static void main(String[] args) {
        //TODO 1 创建属性对象
        Properties prop = new Properties();
        //Todo 2 设置属性参数
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"node2:9092");
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        //todo 配置消费者组
        prop.put(ConsumerConfig.GROUP_ID_CONFIG,"cgout");
        //是否自动提交offset： true表示自动提交，false表示非自动提交
        prop.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,true);
        //提交offset的时间周期1000ms,默认是5000ms
        prop.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,1000);
        //todo 3 创建消费者对象
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(prop);
        //todo 4 设置消费主题
        kafkaConsumer.subscribe(Arrays.asList("topicA"));
        //todo 5 消费消息
        while (true){
            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofSeconds(1));
            //7.循环输出消息
            for (ConsumerRecord consumerRecord:consumerRecords){
                System.out.println(consumerRecord);
            }
        }
    }
}
