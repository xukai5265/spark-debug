package cn.xukai.spark.test

import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2018/4/20.
  */
object Demo5 extends  App{
  val spark = SparkSession
    .builder
    .appName("DataSort")
    .master("local[*]")
    .getOrCreate()
  spark.sparkContext.setLogLevel("OFF")
  val sc = spark.sparkContext
  val ds = sc.textFile("hdfs://192.168.107.128:9000/data/res/")
  ds.coalesce(1).map(x => x.split("\t")).map(c => (c(1),c(0))).sortByKey(false)
      .take(100).foreach(x => println(x._2+"----------"+x._1))
}
