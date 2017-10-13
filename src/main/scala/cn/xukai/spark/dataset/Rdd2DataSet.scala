package cn.xukai.spark.dataset

import cn.xukai.spark.core.DataFrameDemo.Person
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2017/10/12.
  */
object Rdd2DataSet {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[2]")
    conf.set("spark.sql.warehouse.dir","file:/D:/spark/warehouse")
    conf.set("spark.sql.shuffle.partitions","20")
    val sparkSession = SparkSession.builder().appName("RDD to DataFrame")
      .config(conf).getOrCreate()
    //通过代码的方式,设置Spark log4j的级别
    sparkSession.sparkContext.setLogLevel("WARN")
//    sparkSession.read.parquet("").as[Person]
  }


}
