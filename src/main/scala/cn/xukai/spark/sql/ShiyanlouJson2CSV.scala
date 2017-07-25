package cn.xukai.spark.sql

import java.net.URI

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2017/7/21.
  */
object ShiyanlouJson2CSV {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .master("spark://192.168.107.128:7077")
      .appName("json转换csv")
      .getOrCreate()

    val jsonData = spark.read.json("hdfs://192.168.107.128:9000/data/shiyanlou/web_access.json")
    jsonData.createOrReplaceTempView("jsonData")
    val path = "hdfs://192.168.107.128:9000/data/shiyanlou/json2csv"
    val outFile = new Path(path)
    val hdfs = FileSystem.get(new URI("hdfs://192.168.107.128:9000"),new Configuration())
    if(hdfs.exists(outFile)){
      hdfs.delete(outFile,true)
  }
    jsonData.write.csv(path)
//    jsonData.write.format("csv").save(path)
    spark.close()
  }
}
