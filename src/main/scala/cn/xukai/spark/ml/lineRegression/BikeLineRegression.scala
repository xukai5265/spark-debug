package cn.xukai.spark.ml.lineRegression

import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2018/1/16.
  */
object BikeLineRegression extends App{
  val spark = SparkSession.builder().master("local[*]").appName("BikeLineRegression").getOrCreate()
  spark.sparkContext.setLogLevel("WARN")
  var bikeRdd = spark.read.option("header",true).csv("D:\\资料\\语料\\自行车出租数据\\Bike-Sharing-Dataset\\hour.csv")
  bikeRdd.show(false)
  var clazzVar = bikeRdd.select("season","yr","mnth","hr","holiday","weekday","workingday","weathersit")
  clazzVar.show(false)
  println(clazzVar.count())
  println(clazzVar.distinct().count())
  spark.stop()
}
