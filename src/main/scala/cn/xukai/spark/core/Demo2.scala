package cn.xukai.spark.core

import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2017/9/6.
  * 在Demo1 的基础上添加了action函数
  * 其中属于faction操作的有
  * reduce、collect、count、first、take、takeSimple、
  * saveAsTexyFile、saveAsSequenceFile、saveAsObjectFile、
  * countByKey、foreach
  */
object Demo2 extends App{
  val spark = SparkSession.builder().appName("demo2").master("spark://192.168.107.128:7077").getOrCreate()
  spark.sparkContext.addJar("D:\\workspace\\sparkutil\\target\\spark.util-1.0-SNAPSHOT.jar")
  val rdd = spark.sparkContext.parallelize(Seq(("a",1),("b",2),("c",3)))
  val flag = 0
  val key = rdd.map{ line =>
    println("running...")
    line._1
  }
  key.collect().foreach(println)
  spark.close()
}
