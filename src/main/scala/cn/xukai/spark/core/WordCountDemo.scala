package cn.xukai.spark.core

import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2017/12/28.
  */
object WordCountDemo {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().master("local[*]").appName("wordcount").getOrCreate()
    val words = Array("the","me","a","b","the")

    val sc = sparkSession.sparkContext
    val wordsRdd = sc.parallelize(words)
    wordsRdd.map(x => (x,1)).reduceByKey(_ + _).collect().foreach(println)

  }
}
