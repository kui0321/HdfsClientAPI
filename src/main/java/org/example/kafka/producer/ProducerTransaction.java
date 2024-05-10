package org.example.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class ProducerTransaction {
    public static void main(String[] args) {
        //TODO 1.声明并实例化Kafka Producer的配置文件对象
        Properties prop = new Properties();
        //TODO 2.为配置文件对象设置参数
        // 2.1 配置bootstrap_servers
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"node2:9092,node3:9092,node4:9092");
        // 2.2 配置key和value的序列化类
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        //A1.设置事务id
        prop.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "transaction_id_topicA_0");
        //TODO 3.声明并实例化生产者对象
        KafkaProducer<String,String> producer = new KafkaProducer<String, String>(prop);
        //A2.初始化事务
        producer.initTransactions();
        //A3.开启事务
        producer.beginTransaction();
        //A4.添加因此处理，成功提交事务，失败回滚事务
        try {
            //TODO 4.发送消息
            for (int i = 0; i < 5; i++) {
                //同步发送消息
                producer.send(new ProducerRecord<>("topicA","sync_msg"+i)).get();
            }
            //A4.1提交事务
            producer.commitTransaction();
        } catch (Exception e) {
            producer.abortTransaction();
        }
        //TODO 5.关闭生产者
        producer.close();
    }
}
