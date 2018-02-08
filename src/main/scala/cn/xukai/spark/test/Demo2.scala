package cn.xukai.spark.test

import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2018/1/4.
  */
object Demo2 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("test").master("local[*]").getOrCreate()
    // 做法一：
    val sc = spark.sparkContext
    sc.setLogLevel("WARN")
    val A = sc.textFile("D:\\test_data\\scala\\A.txt")
    val B = sc.textFile("D:\\test_data\\scala\\B.txt")
    val pairs_a = A.map(line =>line.split(" ")(0)).map(str => (str,1))
    val pairs_b = B.map(line =>line.split(" ")(0)).map(str => (str,1))
    pairs_a.cache()
    pairs_b.cache()
    //各个文件的ip数
    println("去重之前："+A.count())
    println("去重之后："+A.distinct().count())

    val joinres = pairs_b.leftOuterJoin(pairs_a)
  }
}
