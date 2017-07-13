package cn.xukai.spark

import org.apache.spark.{SparkConf, SparkContext}
/**
  * Created by kaixu on 2017/7/12.
  * step1 . 开启debug 模式
  * spark-submit --class cn.xukai.spark.SimpleApp --master local[4] \
    --driver-java-options "-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=9091" \
    /application/spark-2.2.0-bin-hadoop2.7/examples/jars/spark.util-1.0-SNAPSHOT.jar

    step2. 执行完step1. 后会停留在 Listening for transport dt_socket at address: 9091
    在 remote 中配置 spark master 的ip 及 9091 端口，点击debug ，step1 继续向下执行，直到停留到断点位置。
  */
object SimpleApp {
  def main(args: Array[String]) {
    val logFile = "hdfs://192.168.107.128:9000/data/aaa"
    val conf = new SparkConf().setAppName("simpleApp").setJars(Seq("D:\\workspace\\sparkutil\\target\\spark.util-1.0-SNAPSHOT.jar"))
      .setMaster("spark://192.168.107.128:7077")
    val sc = new SparkContext(conf)
    val logData = sc.textFile(logFile).cache()
    val numAs = logData.filter(line => line.contains("a")).count()
    val numBs = logData.filter(line => line.contains("b")).count()
    println(s"Lines with a: $numAs, Lines with b: $numBs")
    sc.stop()
  }
}
