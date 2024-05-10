package org.example.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class UnSyncCallbackCustomProducer {
    public static void main(String[] args) throws InterruptedException {
        //TODO 1 声明并实例化kafka Producer的配置文件对象
        Properties prop = new Properties();
        //TODO 2 为配置文件设置参数
        //2.1 配置bootstrap_server服务
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"node2:9092,node3:9092,node4:9092");
        //2.2 配置key和value的序列化类
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //TODO 3 声明并实例化生产者对象
        KafkaProducer<String,String> producer = new KafkaProducer<String, String>(prop);
        //TODO 4 发送消息
        for (int i = 0; i < 10; i++) {
            producer.send(new ProducerRecord<>("topicA", "unsync_msg" + i), new Callback() {
                //如下方法为在生产者收到acks确认时异步调用
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e == null) {
                        //无异常信息，输出主题和分区信息到控制台
                        System.out.println("topic"+recordMetadata.partition());
                    }else {
                        //打印异常信息
                        System.out.println(e.getMessage());
                    }
                }
            });
            Thread.sleep(2);
        }
        //TODO 5 关闭生产者
        producer.close();
    }
}
