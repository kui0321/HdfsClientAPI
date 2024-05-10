package org.example.kafka.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerToPartition {
    public static void main(String[] args) throws InterruptedException {
        //TODO 1 声明并实例化kafaka ProDucer的配配置文件对象
        Properties prop = new Properties();
        //TODO 2 为配置文件对象设参数
        //2.1 配置bootstarp_servers
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"node2:9092,node3:9092,node4:9092");
        //2.2 配置key和value的序列化类
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //TODO 3 声明并实例化生产者对象
        KafkaProducer<String, String> producer = new KafkaProducer<>(prop);
        //TODO 4 发送消息
        for (int i = 1; i <= 5; i++) {
            //指定数据发送到0号分区，key为null
            producer.send(new ProducerRecord<>("topicA", 0, null, "unsync_msg" + i), new Callback() {
                //如下方法在生产者收到acks确认时异步调用
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e == null) {
                        //无异常信息，输出主题和分区信息到控制台
                        System.out.println("topic"+recordMetadata.topic()+",partition:"+recordMetadata.partition());
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
