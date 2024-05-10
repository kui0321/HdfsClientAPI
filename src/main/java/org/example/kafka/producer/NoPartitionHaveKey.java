package org.example.kafka.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class NoPartitionHaveKey {
    public static void main(String[] args) throws InterruptedException {
        //TODO 1.声明实例化kafka Producer的配置文件对象
        Properties prop = new Properties();
        //TODO 2.为配置文件设置参数
        //2.1配置bootstrap-servers
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"node2:9092,node3:9092,node4:9092");
        //配置key和value的序列化类
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        //TODO 3 声明并实例化生产对象
        KafkaProducer<String, String> producer = new KafkaProducer<>(prop);
        //TODO 4 发送消息
        for (int i = 0; i < 5; i++) {
            //未指定分区号，key为"gtjin"测试运行一次，改为"kafka"后再测试一次。
            producer.send(new ProducerRecord<>("topicA", "kafka", "unsync_msg" + i), new Callback() {
                //如下方法在生产者收到acks确认时异步调用
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e == null) {
                        System.out.println("topic:"+recordMetadata.topic()+", partition:"+recordMetadata.partition());
                    }else {
                        System.out.println(e.getMessage());
                    }
                }
            });
            Thread.sleep(5);
        }
        //TODO 5 关闭生产者
        producer.close();
    }
}
