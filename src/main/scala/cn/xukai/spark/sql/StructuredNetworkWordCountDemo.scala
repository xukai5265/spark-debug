package cn.xukai.spark.sql

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2017/9/5.
  */
object StructuredNetworkWordCountDemo extends App{
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  if (args.length < 2) {
    System.err.println("Usage: StructuredNetworkWordCount <hostname> <port>")
    System.exit(1)
  }
  val host = args(0)
  val port = args(1).toInt
  val spark = SparkSession.builder().appName("StructuredNetworkWordCountDemo")
    .master("spark://192.168.107.128:7077").getOrCreate()
  import spark.implicits._
  val lines = spark.readStream.format("socket").option("host",host).option("port",port).load()
  val words = lines.as[String].flatMap(_.split(" "))

  val wordCounts = words.groupBy("value").count()

  val query = wordCounts.writeStream
    .outputMode("complete")
    .format("console")
    .start()

  query.awaitTermination()
}
