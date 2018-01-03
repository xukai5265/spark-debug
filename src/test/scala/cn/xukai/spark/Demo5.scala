package cn.xukai.spark

import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2018/1/3.
  */
object Demo5 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[*]").appName("count").getOrCreate()
    val sc = spark.sparkContext
    val file = sc.textFile("D:\\test_data\\spark\\wordcount\\wc.txt",3)
    val count = file.count()
    println(count)
    while(true){
      Thread.sleep(10000)
      println(System.nanoTime())
    }
  }
}
