package cn.xukai.spark.core

import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2017/9/7.
  */
object Demo4 extends App{
  val spark = SparkSession.builder().master("local[2]").appName("demo4").getOrCreate()
  val rdd = spark.sparkContext.parallelize(Seq(1,2,3,4,5))
  rdd.foreach(println)
  spark.close()
}
