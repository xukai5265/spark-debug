package cn.xukai.spark.streaming

import cn.xukai.spark.streaming.jdbc4mysql.ScalaConnectPool.query
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.slf4j.LoggerFactory

/**
  * 使用Spark Streaming 定时统计出每个蓝球中红球出现的次数
  * Created by kaixu on 2018/1/19.
  */
object CountRedByBlueUseStreaming extends App{
  val logger = LoggerFactory.getLogger(this.getClass)
  val conf = new SparkConf().setAppName("double-ball”).setMaster(“local[*]")
  // 每天统计一次
  val ssc = new StreamingContext(conf,Seconds(60*60*24))

//  ssc.textFileStream()

  val rs = query("select code,blue,red from double_ball order by code desc")
  rs.foreach{ resultSet =>
    println("code :"+resultSet.code)
    println("blue :"+resultSet.blue)
    println("reds :"+resultSet.reds)
  }


}
