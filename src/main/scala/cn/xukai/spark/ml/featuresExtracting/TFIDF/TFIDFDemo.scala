package cn.xukai.spark.ml.featuresExtracting.TFIDF

import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator
import org.apache.spark.ml.feature.{HashingTF, IDF, Tokenizer}
import org.apache.spark.ml.param.Param
import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2018/1/16.
  */
object TFIDFDemo extends App{
  case class RawDataRecord(label: Int, text: String)
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  val spark = SparkSession.builder().master("local[*]").appName("TokenizeDemo").getOrCreate()
  val sc = spark.sparkContext
  sc.setLogLevel("OFF")
  import spark.implicits._
  val srcRDD =  sc.textFile("D:\\资料\\语料\\tf-idf.txt").map {
    x =>
      val data = x.split(",")
      RawDataRecord(data(0).toInt, data(1))
  }.toDF()
  val testDF = sc.textFile("D:\\资料\\语料\\test-tf-idf.txt").map {
    x =>
      val data = x.split(",")
      RawDataRecord(data(0).toInt, data(1))
  }.toDF()
  val tokenizer = new Tokenizer().setInputCol("text").setOutputCol("words")
  var wordsData = tokenizer.transform(srcRDD)
  //将每个词转换成Int型，并计算其在文档中的词频（TF）
  var hashingTF = new HashingTF().setInputCol("words").setOutputCol("rawFeatures").setNumFeatures(20)
  var featurizedData = hashingTF.transform(wordsData)
  featurizedData.select($"label", $"words", $"rawFeatures").take(2).foreach(println)
  //计算TF-IDF值
  var idf = new IDF().setInputCol("rawFeatures").setOutputCol("features")
  var idfModel = idf.fit(featurizedData)
  var rescaledData = idfModel.transform(featurizedData)
  rescaledData.select($"label", $"words", $"features").take(2).foreach(println)

  val lr = new LogisticRegression().setMaxIter(100)

  //设置机器学习数据流水线
  val pipeline = new Pipeline().setStages(Array(tokenizer, hashingTF, idf, lr))
  val pa : Array[Param[_]]= pipeline.params
  for(a <- pa){
    println("模型参数： "+ a.name+"::::"+a.toString())
  }
  val lrModel = pipeline.fit(srcRDD)
  val result = lrModel.transform(testDF)
  result.select("label", "prediction","rawPrediction","probability").show(10,false)
  val evaluator = new BinaryClassificationEvaluator()
  var aucTraining = evaluator.evaluate(result)
  println("aucTraining = "+aucTraining)

}
