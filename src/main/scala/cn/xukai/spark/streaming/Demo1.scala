package cn.xukai.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by kaixu on 2017/7/17.
  * spark streaming DStreams 概念
  *   表示连续的数据流，由一些列的rdd来表示，每个rdd 中间会包含一定的时间间隔
  *   对DStream应用的任何操作都将转换为底层RDD上的操作。
  *   sparkStreaming 支持从 socket 中获取流 如：socketTextStream
  *   sparkStreaming 支持从 文件 中获取 流 如：fileStream 监控某一个目录
  *   sparkStreaming 支持 rdd 队列中获取数据流。推送到队列中的每个RDD将被视为DStream中的一批数据，
  *   并像流一样处理。 如： queueStream
  */
object Demo1 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setMaster("spark://192.168.107.128:7077")
      .setAppName("streaming demo1")
      .setJars(Seq("D:\\workspace\\sparkutil\\target\\spark.util-1.0-SNAPSHOT.jar"))
    val ssc = new StreamingContext(conf,Seconds(10))
    val lines = ssc.socketTextStream("localhost", 9999)
    val words = lines.flatMap(_.split(" "))
    val pairs = words.map(word => (word, 1))
    val wordCounts = pairs.reduceByKey(_ + _)
    wordCounts.print()
    ssc.start()
    //等待处理停止（手动或由于任何错误）
    ssc.awaitTermination()
  }
}
