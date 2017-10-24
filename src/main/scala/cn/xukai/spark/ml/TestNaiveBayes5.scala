package cn.xukai.spark.ml

import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.NaiveBayes
import org.apache.spark.ml.feature.{HashingTF, IDF, Tokenizer}
import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2017/10/16.
  * 改进 TestNaiveBayes5.scala
  */
object TestNaiveBayes5 extends App{
  case class RawDataRecord(label: Int, text: String)
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  val spark = SparkSession.builder().master("local[*]").appName("TokenizeDemo").getOrCreate()
  val sc = spark.sparkContext
  val sentenceDataFrame =  sc.textFile("file:/D:/spark/warehouse/trainData.txt").map {
    x =>
      val data = x.split(",")
      data.init
      RawDataRecord(data(0).toInt, data(1))
  }
  import spark.implicits._
  val srcRDD = sentenceDataFrame.toDF()
  val tokenizer = new Tokenizer().setInputCol("text").setOutputCol("words")
  val hashingtf = new HashingTF().setInputCol(tokenizer.getOutputCol).setOutputCol("features")
  // 计算每个次的idf值
  val idf = new IDF().setInputCol("features").setOutputCol("idf-features")
  val bayesModel = new NaiveBayes()
  //设置机器学习数据流水线
  val pipeline = new Pipeline()
    .setStages(Array(tokenizer, hashingtf, idf, bayesModel))
  val lrModel = pipeline.fit(srcRDD)
  lrModel.write.overwrite().save("spark-bayes-model")
  // 预测
  println("预测...")
  val test = spark.sparkContext.textFile("file:/D:/spark/warehouse/testData.txt").toDF("text").select("text")
  val predictions = lrModel.transform(test)
  predictions.show(10,false)
  predictions.printSchema()

}
