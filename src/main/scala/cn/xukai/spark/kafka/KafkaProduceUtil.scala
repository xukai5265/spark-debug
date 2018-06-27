package cn.xukai.spark.kafka

import java.util.Properties
import java.util.concurrent.ExecutionException

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

/**
  * Created by kaixu on 2017/8/1.
  */
object KafkaProduceUtil {
  val props = new Properties
//  props.put("bootstrap.servers", "192.168.107.128:9092")
  props.put("bootstrap.servers", "hadoop-5:6667")
  props.put("client.id", "DemoProducer")
  props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("acks","-1")
  private var producer = new KafkaProducer[Integer, String](props)

  /**
    * 写数据到 kafka
    *
    * @param topic
    * @param message
    */
  def write2kafka(topic:String,message:String,isAsync:Boolean):Unit={
    val startTime = System.currentTimeMillis
    if (isAsync) { // Send asynchronously
      producer.send(new ProducerRecord[Integer, String](topic, message), new DemoCallBack(startTime, 0,message))
    }
    else { // Send synchronously
      try {
        producer.send(new ProducerRecord[Integer, String](topic,message)).get
        System.out.println("Sent message: "+message)
      } catch {
        case e@(_: InterruptedException | _: ExecutionException) =>
          e.printStackTrace()
      }
    }
  }

  def main(args: Array[String]): Unit = {

  }
}
