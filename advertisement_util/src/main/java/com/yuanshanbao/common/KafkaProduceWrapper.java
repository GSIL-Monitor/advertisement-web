package com.yuanshanbao.common;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * Created by liwei on 16/8/22.
 */
public class KafkaProduceWrapper<T> {

    private Producer<String, T> producer;

    public KafkaProduceWrapper(){
        //todo 从文件读取配置信息
        Properties props = new Properties();
        //配置kafka server地址
        props.put("bootstrap.servers", "172.28.49.1:9092,172.28.49.2:9092,172.28.49.3:9092");
        //要求所有acks都返回
        props.put("acks", "all");
        //此处不配置重试,避免发生消息顺序变化,发送失败交由业务处理
        props.put("retries", 0);
        //序列化方式
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer<String, T>(props);
    }

    /**
     * 确保发送消息成功
     * @param topic
     * @param key
     * @param data
     * @return true 发送成功, false 发送失败
     */
    public boolean sendSyn(String topic, String key, T data){
        try {
            ProducerRecord<String, T> record = new ProducerRecord<String, T>(topic, key, data);
            RecordMetadata metaData = producer.send(record).get();
            return true;
        } catch (InterruptedException e) {
            System.out.println("send failed topic:" + topic + ", value:"+ data);
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println("send failed topic:" + topic + ", value:" + data);
            e.printStackTrace();
        }
        return false;
    }
}
