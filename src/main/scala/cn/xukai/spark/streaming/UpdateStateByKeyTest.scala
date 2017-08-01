package cn.xukai.spark.streaming

import com.alibaba.fastjson.{JSON}
import kafka.serializer.StringDecoder
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by kaixu on 2017/8/1.
  * http://blog.csdn.net/lsshlsw/article/details/48298929
  * http://blog.csdn.net/lsshlsw/article/details/48313851
  */
object UpdateStateByKeyTest {

  def main(args: Array[String]): Unit = {
    if (args.length < 2) {
      System.err.println(s"""
                            |Usage: DirectKafkaWordCount <brokers> <topics>
                            |  <brokers> is a list of one or more Kafka brokers
                            |  <topics> is a list of one or more kafka topics to consume from
                            |
        """.stripMargin)
      System.exit(1)
    }
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)

    val Array(brokers, topics) = args
    // Create context with 2 second batch interval
    val sparkConf = new SparkConf().setAppName("DirectKafkaWordCount")
    val ssc = new StreamingContext(sparkConf, Seconds(10))
    ssc.checkpoint("hdfs://192.168.107.128:9000/kaixu/checkpoint/paymount")
    // Create direct kafka stream with brokers and topics
    val topicsSet = topics.split(",").toSet
    val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers)
    val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc, kafkaParams, topicsSet)

    val lines = messages.map(line => (line._2))
    val paymentSum = lines.map(line => {
      val jsonObject = JSON.parseObject(line)
      val user = jsonObject.getString("user")
      val payment = jsonObject.getString("payment")
      (user,payment.toDouble)
    }).reduceByKey(_+_)
    //输出每分钟的计算结果
    println("----------------10秒钟结果----------------")
    paymentSum.print()
    //将以前的数据和最新一分钟的数据进行求和
    val addFunction = (currValues : Seq[Double],preVauleState : Option[Double]) => {
      val currentSum = currValues.sum
      val previousSum = preVauleState.getOrElse(0.0)
      Some(currentSum + previousSum)
    }

    val totalPayment = paymentSum.updateStateByKey[Double](addFunction)
    //输出总计的结果
    println("----------------总和结果-----------------")
    totalPayment.print()
    //排序 ，按照value降序
    val sortRes = totalPayment.transform(rdd =>{
      rdd.map(x => (x._2,x._1)).sortByKey(false).map(y=>(y._2,y._1))
    })
    sortRes.print()


    ssc.start()
    ssc.awaitTermination()
  }
}
