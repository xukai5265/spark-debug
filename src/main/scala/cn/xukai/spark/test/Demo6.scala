package cn.xukai.spark.test

import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2018/4/28.
  */
object Demo6 extends App{
  val spark = SparkSession
    .builder
    .appName("DataSort")
    .master("local[*]")
    .getOrCreate()
  spark.sparkContext.setLogLevel("OFF")
  val sc = spark.sparkContext
  val data = sc.parallelize(Seq("B D A C E H F I","E H F J I K L"),2)
  data.flatMap(word => word.split(" ")).map(x => (x,1)).reduceByKey(_+_).foreach(x => println(x))

  data.flatMap(word => word.split(" ")).map(x => (x,1)).reduceByKey(_+_).coalesce(1).sortByKey().foreach(x => println(x))

  spark.close()
}
