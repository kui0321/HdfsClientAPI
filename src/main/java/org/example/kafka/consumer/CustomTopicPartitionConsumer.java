package org.example.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 创建一个独立消费者，消费topicA主题0号分区中的数据
 */
public class CustomTopicPartitionConsumer {
    public static void main(String[] args) {
        //TODO 1.创建属性对象
        Properties prop = new Properties();
        //TODO 2.设置相关参数
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"node2:9092");
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        prop.put(ConsumerConfig.GROUP_ID_CONFIG,"testCg2");
        //TODO 3.创建消费者对象
        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(prop);
        //TODO 4.为消费者注册主题和分区号
        List<TopicPartition> topicPartitions = new ArrayList<>();
        topicPartitions.add(new TopicPartition("topicA",0));
        consumer.assign(topicPartitions);
        //TODO 5.消费数据
        while (true){
            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord consumerRecord: consumerRecords) {
                System.out.println(consumerRecord.value());
            }
        }
    }
}
