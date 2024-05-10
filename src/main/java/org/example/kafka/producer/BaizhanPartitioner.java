package org.example.kafka.producer;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class BaizhanPartitioner implements Partitioner {
    private Random random;

    /** 计算信息对应的分区
     * @param topic     主题
     * @param key       消息的key
     * @param keyBytes 消息的key序列化后的字节数组
     * @param value     消息的value
     * @param valueBytes 消息value序列化后的字节数组
     * @param cluster   集群元数据 可以获取分区信息
     * @return 息对应的分区号
     */
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        //将key转换为字符串
        String keyInfo = (String) key;
        //获取指定主题的分区对象列表
        List<PartitionInfo> partitionInfoList = cluster.availablePartitionsForTopic(topic);
        //获取指定主题下的分区总数量
        int partCount = partitionInfoList.size();
        //最后一个分区号
        int baizhanPartition = partCount - 1;
        return keyInfo == null || keyInfo.isEmpty() || !keyInfo.contains("baizhan") ?
                random.nextInt(partCount-1) :
                baizhanPartition ;
    }

    @Override
    public void close() {
        //该方法实现必要资源的清理工作
        random = null;
    }

    @Override
    public void configure(Map<String, ?> map) {
        //该方法实现必要资源的初始化工作
        random = new Random();
    }
}
