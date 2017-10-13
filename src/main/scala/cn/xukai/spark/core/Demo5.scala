package cn.xukai.spark.core

import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2017/9/8.
  */
object Demo5 extends App{
  val spark = SparkSession.builder().master("local[2]").appName("demo5").getOrCreate()
//  val data = spark.sparkContext.textFile("D:\\资料\\语料\\探针数据.log")
  val file = spark.read.text("D:\\资料\\语料\\探针数据.log").rdd
  val mapped = file.map(s => s.length).cache()
  for (iter <- 1 to 10) {
    val start = System.currentTimeMillis()
    for (x <- mapped) { x + 2}
    val end = System.currentTimeMillis()
    println("Iteration " + iter + " took " + (end-start) + " ms")
  }
  spark.stop()
}
