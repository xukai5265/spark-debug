package cn.xukai.spark.ml.logisiticRegression

import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{HashingTF, IDF, Tokenizer}
import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2018/1/9.
  *
  */
object LogisiticRegressionTest3 extends App{
  case class RawDataRecord(label: Int, text: String)
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  val spark = SparkSession.builder().master("local[*]").appName("TokenizeDemo").getOrCreate()
  val sc = spark.sparkContext
  sc.setLogLevel("OFF")
  import spark.implicits._
  val srcRDD =  sc.textFile("D:\\资料\\语料\\sougou-train").map {
    x =>
      val data = x.split(",")
      data.init
      RawDataRecord(data(0).toInt, data(1))
  }.toDF()

  val tokenizer = new Tokenizer().setInputCol("text").setOutputCol("words")
  val hashingtf = new HashingTF().setInputCol(tokenizer.getOutputCol).setOutputCol("features")
  // 计算每个次的idf值
  val idf = new IDF().setInputCol("features").setOutputCol("idf-features")


  val splits = srcRDD.randomSplit(Array(0.7, 0.3),11L)
  var trainingDF = splits(0).toDF()
  trainingDF.cache()
  var testDF = splits(1).toDF()
  println("srcRDD: "+srcRDD.count()+" trainingDF: "+trainingDF.count() + " testDF: "+ testDF.count())

  // 实例化逻辑回归算法
  val lr = new LogisticRegression()
  //设置机器学习数据流水线
  val pipeline = new Pipeline().setStages(Array(tokenizer, hashingtf, idf, lr))
  val lrModel = pipeline.fit(trainingDF)
  println("预测...")

  val result = lrModel.transform(testDF)
  result.select("label", "prediction","rawPrediction","probability").show(10,false)
  val evaluator = new MulticlassClassificationEvaluator()
  var aucTraining = evaluator.evaluate(result)
  println("aucTraining = "+aucTraining)



}
