package cn.xukai.spark.kafka

import java.util.Properties

import cn.xukai.spark.kafka.KafkaProducer.{producer, topic}
import kafka.producer.{KeyedMessage, Producer, ProducerConfig}

/**
  * Created by kaixu on 2017/8/1.
  */
object KafkaProduceUtil {
  //kafka参数
  val brokers = "192.168.107.128:9092"
  val props = new Properties()
  props.put("metadata.broker.list", brokers)
  props.put("serializer.class", "kafka.serializer.StringEncoder")
  val kafkaConfig = new ProducerConfig(props)
  val producer = new Producer[String, String](kafkaConfig)
  def write2kafka(offset:String,topic:String):Unit={
    producer.send(new KeyedMessage[String, String](topic, offset))
  }

  def main(args: Array[String]): Unit = {
    write2kafka("a","offset_topic");
  }
}
