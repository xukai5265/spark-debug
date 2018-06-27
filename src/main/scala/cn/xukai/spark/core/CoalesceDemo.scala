package cn.xukai.spark.core

import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2018/4/2.
  */
object CoalesceDemo extends App{
  val spark = SparkSession
    .builder()
    .appName("rddDemo")
    .master("local[*]")
    .getOrCreate()
  val sc = spark.sparkContext
  val data = sc.textFile("D:\\资料\\语料\\sougou-train")
  println(s"分区数量:${data.partitions.length}")
  val data1 = data.coalesce(1)
  println(s"分区数量:${data1.partitions.length}")
  // coalesce(100,false) shuffle=false 时，如果传入参数大于现有分区数量，rdd的分区数量不变
  val data2 = data.coalesce(100)
  println(s"分区数量:${data2.partitions.length}")
  val data3 = data.coalesce(100,true)
  println(s"分区数量:${data3.partitions.length}")

}
