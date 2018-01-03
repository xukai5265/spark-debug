package cn.xukai.spark.dataframe

import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2017/10/11.
  */
object DataFrameDemo{
  def main(args: Array[String]): Unit = {
    val spark:SparkSession = SparkSession.builder().master("local[*]").appName("csvDataFrame").getOrCreate()
    val sfpd = spark.read.option("header", "true").csv("D:\\test_data\\spark\\datasf\\SFPD.csv")
    sfpd.show(false)
    sfpd.createOrReplaceTempView("sfpd")
    println(spark.sql("select count(*) from sfpd"))

//    while (true){
//      Thread.sleep(10000)
//      println(System.nanoTime())
//    }
  }
}
