package org.example.kafka;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class SyncCustomProducer {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //TODO 1.声明并实例化Kafka Producer的配置文件对象
        Properties prop = new Properties();
        //TODO 2.为配置文件对象设置参数
        // 2.1 配置bootstrap_servers
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"node2:9092,node3:9092,node4:9092");
        // 2.2 配置key和value的序列化类
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        //TODO 3.声明并实例化生产者对象
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(prop);
        //TODO 4.发送消息
        for (int i = 0; i < 5; i++) {
            // //同步发送消息
            producer.send(new ProducerRecord<>("topicA","sync_msg"+i)).get();
        }
        //TODO 5.关闭生产者
        producer.close();
    }
}
