package cn.xukai.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by kaixu on 2018/6/4.
  */
object Demo3 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setMaster("local[*]")
      .setAppName("streaming demo1")
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
