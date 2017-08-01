package cn.xukai.spark.kafka

import java.util.Properties

import com.alibaba.fastjson.JSONObject
import kafka.producer.{KeyedMessage, Producer, ProducerConfig}

import scala.util.Random


/**
  * Created by kaixu on 2017/8/1.
  */
object KafkaProducer extends App{
  private val users = Array("A","B","C","D","E","F","G","H","I","G","K"
  ,"L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z")
  private val random = new Random()

  //设置消费金额
  def payMount():Double={
    random.nextInt(10)
  }
  //获取用户名
  def getUserName():String={
    var name :String = ""
    name+=users(random.nextInt(users.length))+users(random.nextInt(users.length))
    name
  }
  //kafka参数
  val topic = "user_payment"
  val brokers = "192.168.107.128:9092"
  val props = new Properties()
  props.put("metadata.broker.list", brokers)
  props.put("serializer.class", "kafka.serializer.StringEncoder")
  val kafkaConfig = new ProducerConfig(props)
  val producer = new Producer[String, String](kafkaConfig)
  while (true){
    // 创建json串
    val event = new JSONObject()
    event.put("user", getUserName())
    event.put("payment", payMount)

    // 往kafka发送数据
    producer.send(new KeyedMessage[String, String](topic, event.toString))
    println("Message sent: " + event)

    //每隔200ms发送一条数据
    Thread.sleep(200)
  }
}
