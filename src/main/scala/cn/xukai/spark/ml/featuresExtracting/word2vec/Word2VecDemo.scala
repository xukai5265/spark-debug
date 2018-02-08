package cn.xukai.spark.ml.featuresExtracting.word2vec

import org.apache.spark.mllib.feature.Word2Vec
import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2018/1/23.
  */
object Word2VecDemo extends App{
  val spark = SparkSession.builder().master("local[*]").appName("Word2VecDemo").getOrCreate()
  val sc = spark.sparkContext
  sc.setLogLevel("OFF")
  val w2vec = new Word2Vec()

}
