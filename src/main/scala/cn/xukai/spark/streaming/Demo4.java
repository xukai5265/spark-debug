package cn.xukai.spark.streaming;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

import java.util.Properties;

/**
 * Created by kaixu on 2018/6/26.
 */
public class Demo4 {
    public static void main(String[] args) {
        Properties props = new Properties();
        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        producer.initTransactions();
        producer.beginTransaction();
        Consumer consumer = new KafkaConsumer<String, String>(props);


        // 初始化事务，包括结束该Transaction ID对应的未完成的事务（如果有）
        // 保证新的事务在一个正确的状态下启动

    }
}
