package org.example.kafka.producer;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

public class CounterIntercepter implements ProducerInterceptor<String, String> {
    private int errorCounter = 0;
    private int successCounter = 0;
    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> producerRecord) {
        return producerRecord;
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
        if (e == null) {
            successCounter ++;
        }else{
            errorCounter ++;
        }
    }

    @Override
    public void close() {
        System.out.println("successfulsend:"+successCounter);
        System.out.println("failedsend"+errorCounter);
    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
