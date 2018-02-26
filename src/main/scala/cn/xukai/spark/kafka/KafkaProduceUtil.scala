package cn.xukai.spark.kafka

import java.util.Properties

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

  /**
    * 写数据到 kafka
    * @param topic
    * @param message
    */
  def write2kafka(topic:String,message:String):Unit={
    producer.send(new KeyedMessage[String, String](topic, message))
//    producer.close()
  }

  def main(args: Array[String]): Unit = {
    write2kafka("xk1","hello world - 6")
  }
}
