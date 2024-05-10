package org.example.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * 创建一个独立消费者，消费topicA主题下的数据
 */
public class CustomTopicConsumer {
    public static void main(String[] args) {
        //TODO 1.创建消费者属性文件对象
        Properties prop = new Properties();
        //TODO 2.为属性对象设置相关参数
        //设置kafka服务器
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"node2:9092");
        //设置key和value的序列化类
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        //设置消费者的消费者组的名称
        prop.put(ConsumerConfig.GROUP_ID_CONFIG,"testCg1");
        //TODO 3.创建消费者对象
        KafkaConsumer<String,String> kafkaConsumer = new KafkaConsumer<String, String>(prop);
        //TODO 4.为消费者注册主题和分区号
        kafkaConsumer.subscribe(Arrays.asList("topicx"));
        //TODO 5.拉取数据并打印输出
        while (true){
            ConsumerRecords<String,String> consumerRecords = kafkaConsumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord consumerRecord:consumerRecords) {
                System.out.println(consumerRecord);
            }
        }
    }
}
