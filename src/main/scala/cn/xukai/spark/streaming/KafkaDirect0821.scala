package cn.xukai.spark.streaming

import cn.xukai.spark.kafka.KafkaCluster
import kafka.common.{ErrorMapping, OffsetAndMetadata, TopicAndPartition}
import kafka.serializer.StringDecoder
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.{HasOffsetRanges, KafkaUtils, OffsetRange}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by kaixu on 2017/7/28.
  * kafka版本：
  *   0.8.2.1
  * 执行方式：
  *   采用 direct 方式从kafka topic(test) 获取数据
  * 执行命令：
  * spark-submit --class cn.xukai.spark.streaming.KafkaDirect0821 \
  * --master local[*] \
  * jar \
  * 192.168.107.128:9092 \
  * test
  *
  * 遇到的问题：
  *   1. 当spark streaming 重启时，这段时间内kafka 生产者一直在生产数据，但是这段时间的数据却无法被消费
  *   因为 spark streaming kafka direct stream 是获取最新的topic 的 offset
  * 解决办法：
  *    想法： 将获取到的数据的偏移量写入kafka topic 中。
  */
object KafkaDirect0821 {
  def main(args: Array[String]) {
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
    var offsetRanges = Array[OffsetRange]()
    // Create context with 2 second batch interval
    val sparkConf = new SparkConf().setAppName("DirectKafkaWordCount")
    val ssc = new StreamingContext(sparkConf, Seconds(10))
    // Create direct kafka stream with brokers and topics
    val topicsSet = topics.split(",").toSet
    val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers)
    //获取offset_topic 的最后一个元素  http://www.cnblogs.com/hd-zg/p/6841249.html
//    val connect = ScalaConnectPool.getConnection   从mysql 中获取offset
//    val stmt = connect.createStatement()
//    val resultSet = stmt.executeQuery("select offset from topic_offset order by insert_time desc limit 1")
//    if(resultSet != null){
//      val offset = resultSet.getLong("offset")
//      println("--------------------"+offset+"---------------------------")
//    }
    val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc, kafkaParams, topicsSet)
    // Get the lines, split them into words, count the words and print
    val lines = messages.map(_._2)
    val words = lines.flatMap(_.split(" "))
    val wordCounts = words.map(x => (x, 1L)).reduceByKey(_ + _)
//    wordCounts.print()

    messages.transform(rdd =>{
      offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges //得到该 rdd 对应 kafka 的消息的 offset

      rdd
    }).map(_._2).foreachRDD(rdd => {
      val kc = new KafkaCluster(kafkaParams)
      for (o <- offsetRanges) {
        //写入kafka 中
//        KafkaProduceUtil.write2kafka(o.fromOffset.toString,"offset_topic")
        //写入到mysql中
//          val connect = ScalaConnectPool.getConnection
//          connect.setAutoCommit(false)
//          val stmt = connect.createStatement()
//          stmt.addBatch("insert into topic_offset (insert_time,offset) values (now(),'"+o.fromOffset+"')")
//          stmt.executeBatch()
//          connect.commit()
        // 写入到 zookeeper 中
//            kc.setConsumerOffsets("",Map(TopicAndPartition("iteblog", o.partition),o.fromOffset),1)
      }
      rdd.foreach(s=>println(s))
    })
    // Start the computation
    ssc.start()
    ssc.awaitTermination()
  }
}
