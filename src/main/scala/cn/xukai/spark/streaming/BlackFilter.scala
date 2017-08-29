package cn.xukai.spark.streaming

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by kaixu on 2017/8/3.
  */
object BlackFilter {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    val sparkConf = new SparkConf().setAppName("blackFilter")
    val ssc = new StreamingContext(sparkConf, Seconds(10))

    // 黑名单
    val bl = Array(("Jim", true), ("hack", true))
    val blRdd = ssc.sparkContext.parallelize(bl, 3)
    val messages = ssc.socketTextStream("192.168.107.128", 9999)
    val users = messages.map {
      message => (message.split(" ")(1), message)
    }
    users.print()

    val validRddDS = users.transform(ld => {
      val ljoinRdd = ld.leftOuterJoin(blRdd)
      val fRdd = ljoinRdd.filter(tuple => {
        if (tuple._2._2.getOrElse(false)) {
          false
        } else {
          true
        }
      })
      val validRdd = fRdd.map(tuple => tuple._2._1)
      validRdd
    })
//    打印白名单
    validRddDS.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
