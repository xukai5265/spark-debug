package cn.xukai.spark

import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2017/12/8.
  */
object Demo {
  def main(args: Array[String]): Unit = {
//    for(i <- Range(2,10) if i> 5 )println(i)
    val sparkSession = SparkSession.builder().master("local[*]").appName("demo").getOrCreate()
    val sc = sparkSession.sparkContext
    val data = sc.parallelize(List((1, "www"), (1, "iteblog"), (1, "com"),(2, "bbs"), (2, "iteblog"), (2, "com"), (3, "good")))
    val map = data.collectAsMap()
    println(map)
  }
}
