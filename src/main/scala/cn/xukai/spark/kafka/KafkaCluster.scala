//package cn.xukai.spark.kafka
//
//
//import kafka.api.OffsetCommitRequest
//import kafka.common.{ErrorMapping, OffsetAndMetadata, TopicAndPartition}
//import kafka.consumer.SimpleConsumer
//import org.apache.spark.SparkException
//import org.apache.spark.streaming.kafka.KafkaCluster.SimpleConsumerConfig
//
//import scala.collection.mutable.ArrayBuffer
//import scala.util.Random
//import scala.util.control.NonFatal
//
///**
//  * 将偏移量发送到zookeeper
//  * Created by kaixu on 2017/8/3. pom 中使用 该版本的spark-streaming-kafka的依赖
//  <dependency>
//      <groupId>org.apache.spark</groupId>
//      <artifactId>spark-streaming-kafka-0-8_2.11</artifactId>
//      <version>2.2.0</version>
//  </dependency>
//  */
//class KafkaCluster(val kafkaParams: Map[String, String]) extends Serializable {
//  type Err = ArrayBuffer[Throwable]
//  //在JVM中为transient字段，非序列化的一部分，常用语临时保存的缓存数据，或易于重新计算的数据。
//  @transient
//  private var _config: SimpleConsumerConfig = null
//  def config: SimpleConsumerConfig = this.synchronized {
//    if (_config == null) {
//      _config = SimpleConsumerConfig(kafkaParams)
//    }
//    _config
//  }
//  def setConsumerOffsets(
//                          groupId: String,
//                          offsets: Map[TopicAndPartition, Long],
//                          consumerApiVersion: Short
//                        ): Either[Err, Map[TopicAndPartition, Short]] = {
//    val meta = offsets.map { kv =>
//      kv._1 -> OffsetAndMetadata(kv._2)
//    }
//    setConsumerOffsetMetadata(groupId, meta, consumerApiVersion)
//  }
//
//  def setConsumerOffsetMetadata(
//                                 groupId: String,
//                                 metadata: Map[TopicAndPartition, OffsetAndMetadata],
//                                 consumerApiVersion: Short
//                               ): Either[Err, Map[TopicAndPartition, Short]] = {
//    var result = Map[TopicAndPartition, Short]()
//    val req = OffsetCommitRequest(groupId, metadata, consumerApiVersion)
//    val errs = new Err
//    val topicAndPartitions = metadata.keySet
//    withBrokers(Random.shuffle(config.seedBrokers), errs) { consumer =>
//      val resp = consumer.commitOffsets(req)
//      val respMap = resp.commitStatus
//      val needed = topicAndPartitions.diff(result.keySet)
//      needed.foreach { tp: TopicAndPartition =>
//        respMap.get(tp).foreach { err: Short =>
//          if (err == ErrorMapping.NoError) {
//            result += tp -> err
//          } else {
//            errs.append(ErrorMapping.exceptionFor(err))
//          }
//        }
//      }
//      if (result.keys.size == topicAndPartitions.size) {
//        return Right(result)
//      }
//    }
//    val missing = topicAndPartitions.diff(result.keySet)
//    errs.append(new SparkException(s"Couldn't set offsets for ${missing}"))
//    Left(errs)
//  }
//
//  private def withBrokers(brokers: Iterable[(String, Int)], errs: Err)
//                         (fn: SimpleConsumer => Any): Unit = {
//    brokers.foreach { hp =>
//      var consumer: SimpleConsumer = null
//      try {
//        consumer = connect(hp._1, hp._2)
//        fn(consumer)
//      } catch {
//        case NonFatal(e) =>
//          errs.append(e)
//      } finally {
//        if (consumer != null) {
//          consumer.close()
//        }
//      }
//    }
//  }
//
//  def connect(host: String, port: Int): SimpleConsumer =
//    new SimpleConsumer(host, port, config.socketTimeoutMs,
//      config.socketReceiveBufferBytes, config.clientId)
//}
