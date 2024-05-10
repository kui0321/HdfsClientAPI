package org.example.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerHandSyncCommit {
    public static void main(String[] args) {
        //Todo 1 创建属性对象
        Properties prop = new Properties();
        //Todo 2 设置相关参数
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"node2:9092,node3:9092,node4:9092");
            prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        prop.put(ConsumerConfig.GROUP_ID_CONFIG,"cghandSyncCommit");
        prop.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,false);
        //todo 3 创建消费者对象
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(prop);
        //todo 4 注册消费主题
        consumer.subscribe(Arrays.asList("topicA"));
        //todo 5 消费数据
        while(true){
            ConsumerRecords<String,String> consumerRecords = consumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord consumerRecord: consumerRecords
                 ) {
                System.out.println(consumerRecord.value());
            }
            consumer.commitSync();
        }
    }
}
