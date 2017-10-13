package cn.xukai.spark.ml

import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.classification.NaiveBayes
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{HashingTF, IDF, LabeledPoint, Tokenizer}
import org.apache.spark.ml.linalg.Vector
import org.apache.spark.sql.{Row, SparkSession}

/**
  * Created by kaixu on 2017/8/28.
  * http://lxw1234.com/archives/2016/01/605.htm
  * bloom Filter http://blog.csdn.net/jiaomeng/article/details/1495500
  *
  * NaiveBayes
  */
object TestNaiveBayes3 extends App{
  case class RawDataRecord(category: String, text: String)
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  val spark = SparkSession.builder().master("local[3]").appName("TestNaiveBayes3").getOrCreate()
  val sc = spark.sparkContext
  var srcRDD = sc.textFile("trainData.txt").map{
    x =>
      var data = x.split(",")
      RawDataRecord(data(0),data(1))
  }
  import spark.implicits._
  val trainingData= srcRDD.toDF()
  //将词语转换成数组
  var tokenizer = new Tokenizer().setInputCol("text").setOutputCol("words")
  var wordsData = tokenizer.transform(trainingData)
  println("output1：")
  wordsData.select($"category",$"text",$"words").show(1)

  //计算每个词在文档中的词频
  var hashingTF = new HashingTF().setNumFeatures(500000).setInputCol("words").setOutputCol("rawFeatures")
  var featurizedData = hashingTF.transform(wordsData)
  println("output2：")
  featurizedData.select($"category", $"words", $"rawFeatures").show(1)

  //计算每个词的TF-IDF
  var idf = new IDF().setInputCol("rawFeatures").setOutputCol("features")
  var idfModel = idf.fit(featurizedData)
  var rescaledData = idfModel.transform(featurizedData)
  println("output3：")
  rescaledData.select($"category", $"features").show(1)

  println("output4: ")
  var trainDataRdd = rescaledData.select($"category",$"features").map {
    case Row(label: String, features: Vector) => LabeledPoint(label.toDouble,features)
  }
  val Array(trainingData1, testData) = trainDataRdd.randomSplit(Array(0.7, 0.3), seed = 1234L)
  trainDataRdd.show(1)
  val bayes = new NaiveBayes()
  val bayesModel = bayes.fit(trainingData1)

  val predictions = bayesModel.transform(testData)
  println("output5: ")
  predictions.show(10)


  val evaluator = new MulticlassClassificationEvaluator()
    .setLabelCol("label")
    .setPredictionCol("prediction")
    .setMetricName("accuracy")
  val accuracy = evaluator.evaluate(predictions)
  println("Test set accuracy = " + accuracy)
















//
//  //训练模型
//  val model = NaiveBayes.train(parsedData, lambda = 1.0, modelType = "multinomial")

  //  val parsedData =  sc.textFile("trainData.txt").map {
//    line =>
//      val parts = line.split(",")
//      LabeledPoint(parts(0).toDouble,Vectors.dense(parts(1).split(' ').map(_.toDouble)))
//  }
//  val training = parsedData
//  //获得训练模型,第一个参数为数据，第二个参数为平滑参数，默认为1，可改变
//  val model =NaiveBayes.train(training,lambda = 1.0,modelType = "multinomial")



}
