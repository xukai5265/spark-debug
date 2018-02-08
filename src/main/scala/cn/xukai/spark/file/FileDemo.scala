package cn.xukai.spark.file

import org.apache.spark.SparkFiles
import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2018/1/8.
  */
object FileDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[*]").appName("fileDemo").getOrCreate()
    val sc = spark.sparkContext
    val path = "D:\\test_data\\spark\\a.txt"
    sc.addFile(path)
    val rdd = sc.textFile(SparkFiles.get(path))
    rdd.foreach(println)
    spark.close()
  }
}
