package cn.xukai.spark.ml

import cn.xukai.spark.ansj.AnsjUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2017/9/5.
  */
object TestNaiveBayes2 extends App{
  case class RawDataRecord(id: Int, createtime: String,content:String)

  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  val spark = SparkSession.builder().master("local[*]").appName("TokenizeDemo").getOrCreate()
  val url = "jdbc:postgresql://10.167.202.177:5432/crawler-hx"
  val prop = new java.util.Properties
  prop.put("user","TXDB")
  prop.put("password","123456")
  prop.put("driver","org.postgresql.Driver")
  val test = spark.read.jdbc(url,"test",prop)
  test.show()

  test.createOrReplaceTempView("test")
  // 负面
  val positiveData = spark.sql("select content from test where label ='0'")
  println("负面数据条数："+positiveData.count())
  // 正面
  val negativeData = spark.sql("select content from test where label ='1'")
  println("正面数据条数："+negativeData.count())
  // 测试数据
  val testData = spark.sql("select content from test where label is null")
  println("测试数据条数："+testData.count())

//  positiveData.map( data => AnsjUtils.getFC(data))
  spark.close()
}
