package cn.xukai.spark.streaming

//import org.apache.kafka.common.serialization.StringDeserializer
//import org.apache.spark.SparkConf
//import org.apache.spark.streaming._
//import org.apache.spark.streaming.kafka010.KafkaUtils
//import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
//import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
/**
  * Created by kaixu on 2017/7/26.
  */
object Demo2 {
  def main(args: Array[String]): Unit = {
//    val kafkaParams = Map[String, Object](
//      "bootstrap.servers" -> "192.168.137.128:9092",
//      "key.deserializer" -> classOf[StringDeserializer],
//      "value.deserializer" -> classOf[StringDeserializer],
//      "group.id" -> "xx",
//      "auto.offset.reset" -> "latest",
//      "enable.auto.commit" -> (false: java.lang.Boolean)
//    )
//    val topics = Array("test")
//    val conf = new SparkConf()
//      .setMaster("spark://192.168.107.128:7077")
//      .setAppName("streaming demo1")
//      .setJars(Seq("D:\\workspace\\sparkutil\\target\\spark.util-1.0-SNAPSHOT.jar"))
//    val ssc = new StreamingContext(conf,Seconds(2))
//    //读取kakfa topic 数据
//    ssc.checkpoint("hdfs://192.168.107.128:9000/kaixu/checkpoint")
//    val stream = KafkaUtils.createDirectStream[String, String](
//      ssc,
//      PreferConsistent,
//      Subscribe[String, String](topics, kafkaParams)
//    )
//    val myMap = stream.map(record => (record.key, record.value))
//    myMap.foreachRDD(a=>{
//      println(a)
//    })
//    ssc.start()
//    ssc.awaitTermination()
  }
}
