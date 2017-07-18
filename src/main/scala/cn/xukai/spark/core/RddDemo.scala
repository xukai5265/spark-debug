package cn.xukai.spark.core

import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2017/7/17.
  */
object RddDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("rddDemo")
      .master("spark://192.168.107.128:7077")
      .getOrCreate()
    spark.sparkContext.addJar("D:\\workspace\\sparkutil\\target\\spark.util-1.0-SNAPSHOT.jar")
    val sparkContext = spark.sparkContext
    sparkContext.objectFile("")
    val dataRdd = sparkContext.textFile("hdfs://192.168.107.128:9000/data/aaa")
    val flatMapRdd = dataRdd.flatMap(_.split(" "))
    val d1 = flatMapRdd.map(word => (word, 1))
    //    val d2 = d1.reduceByKey(_+_)
    val d2 = d1.groupByKey()
    d2.collect().foreach(println)
    //写到hdfs 文件
    //    val outFile = new Path("hdfs://192.168.107.128:9000/kaixu/bbb")
    //    val hdfs = FileSystem.get(new java.net.URI("hdfs://192.168.107.128:9000"),new org.apache.hadoop.conf.Configuration())
    //    if(hdfs.exists(outFile)) hdfs.delete(outFile,true)
    //    d2.saveAsTextFile("hdfs://192.168.107.128:9000/kaixu/bbb")
    spark.close()

  }
}
