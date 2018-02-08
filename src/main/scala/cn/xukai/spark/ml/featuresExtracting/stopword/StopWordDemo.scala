package cn.xukai.spark.ml.featuresExtracting.stopword

import org.apache.spark.SparkFiles
import org.apache.spark.ml.feature.StopWordsRemover
import org.apache.spark.sql.SparkSession

import scala.collection.mutable.ArrayBuffer

/**
  * Created by kaixu on 2018/1/16.
  */
object StopWordDemo extends App{
  val spark = SparkSession.builder().master("local[*]").appName("StopWordDemo").getOrCreate()
  val sc = spark.sparkContext
  sc.setLogLevel("OFF")
  val array = new ArrayBuffer[String]()
  val stopwordFile = sc.textFile(SparkFiles.get("D:\\workspace\\sparkutil\\stopword.txt"),1)
  stopwordFile.map(line => array+=(line)).collect()
  val stopwords = array.toArray
  println("停词数量："+stopwords.size)

  val removers = new StopWordsRemover().setInputCol("raw").setOutputCol("filtered").setStopWords(stopwords)
  val dataSet = spark.createDataFrame(Seq(
    (0, Seq("I", "saw", "the", "red", "balloon")),
    (1, Seq("Mary", "had", "a", "little", "lamb")),
    (2,Seq("你","我","他","的","中国",",","，"))
  )).toDF("id", "raw")

  // 加载默认停词 默认支持如下语言： "danish", "dutch", "english", "finnish", "french", "german","hungarian", "italian", "norwegian", "portuguese", "russian", "spanish", "swedish", "turkish"
//  StopWordsRemover.loadDefaultStopWords("")
  removers.transform(dataSet).show(false)
}
