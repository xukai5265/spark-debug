package cn.xukai.spark.ml
import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.classification.NaiveBayes
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{HashingTF, IDF, LabeledPoint, Tokenizer}
import org.apache.spark.sql.types.DoubleType
import org.apache.spark.sql.{Row, SparkSession}

import scala.collection.Map

/**
  * Created by kaixu on 2017/8/28.
  * http://lxw1234.com/archives/2016/01/605.htm
  * bloom Filter http://blog.csdn.net/jiaomeng/article/details/1495500
  *
  * NaiveBayes
  */
object SougouNB extends App{

  case class RawDataRecord(label: Int, text: String)
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  val spark = SparkSession.builder().master("local[*]").appName("TokenizeDemo").getOrCreate()
  val sc = spark.sparkContext
  //   D:\资料\语料\sougou-train
  val sentenceDataFrame =  sc.textFile("file:/D:/spark/warehouse/trainData.txt").map {
    x =>
      val data = x.split(",")
      data.init
      RawDataRecord(data(0).toInt, data(1))
  }
  import spark.implicits._
  val srcRDD = sentenceDataFrame.toDF()

  val tokenizer = new Tokenizer().setInputCol("text").setOutputCol("words")
  var wordsData = tokenizer.transform(srcRDD)

  val hashingtf = new HashingTF().setInputCol(tokenizer.getOutputCol).setOutputCol("features")
  var featurizedData = hashingtf.transform(wordsData)
  println("println 1")
  featurizedData.select($"label",$"words", $"features").show(10,false)

  // 计算每个次的idf值
  val idf = new IDF().setInputCol(hashingtf.getOutputCol).setOutputCol("idf-features")
  var idfModel = idf.fit(featurizedData)
  var rescaledData = idfModel.transform(featurizedData)

  println("println 2 ")
  featurizedData.select($"label",$"features").show(20,false)

  var trainDataRdd = rescaledData.select($"label",$"features").map { x =>
    LabeledPoint(x.getInt(0).toDouble,x.getAs(1))
  }

  val Array(trainingData, testData) = trainDataRdd.randomSplit(Array(0.7, 0.3), seed = 1234L)
  val nb = new NaiveBayes()
  val nbModel = nb.fit(trainingData)

  // testData 中正值数量为：
  val simple_positive = testData.filter($"label"===1.0).count()
  // testData 中负值数量为：
  val simple_negative = testData.filter($"label"===0.0).count()
  val predictions = nbModel.transform(testData)
  val tp = predictions.filter($"label"===$"prediction").filter($"label"===1.0).count()
  println("tp --->"+tp)  // 106
  val testCount = testData.count()
  println("testCount--->"+testCount)
  /**精确率*/
  val percision = 1.0 * tp / testCount
  println("percision ---> "+percision)
  /**召回率*/
  val recall = 1.0 * tp / simple_positive
  println("recall ---> "+recall)
  /**准确率*/
  val accuracy = 1.0*predictions.filter($"label"===$"prediction").count()/testData.count()
  println("accuracy-->"+accuracy)

  val evaluator_accuracy = new MulticlassClassificationEvaluator()
    .setLabelCol("label")
    .setPredictionCol("prediction")
    .setMetricName("accuracy")
  val accuracy_spark = evaluator_accuracy.evaluate(predictions)
  println("Test set accuracy_spark = " + accuracy_spark)

  val evaluator_precision = new MulticlassClassificationEvaluator()
    .setLabelCol("label")
    .setPredictionCol("prediction")
    .setMetricName("weightedPrecision")
  val precision_spark = evaluator_precision.evaluate(predictions)
  println("Test set weightedPrecision_spark = " + precision_spark)

  val evaluator_recall = new MulticlassClassificationEvaluator()
    .setLabelCol("label")
    .setPredictionCol("prediction")
    .setMetricName("weightedRecall")
  val recall_spark = evaluator_recall.evaluate(predictions)
  println("Test set weightedRecall_spark = " + recall_spark)

  val evaluator_f1 = new MulticlassClassificationEvaluator()
    .setLabelCol("label")
    .setPredictionCol("prediction")
    .setMetricName("f1")
  val f1_spark = evaluator_f1.evaluate(predictions)
  println("Test set f1_spark = " + f1_spark)

  val predictionAndLabels =
    predictions.select($"prediction", $"label".cast(DoubleType)).rdd.map {
      case Row(prediction: Double, label: Double) => (prediction, label)
    }
  println(predictionAndLabels.collect().size)
  // 统计预测值 白话：预测正面：数量   预测负面：数量
  val tpByClass: Map[Double, Int] = predictionAndLabels.map{
    case(prediction, label)=>
      (label,if (label==prediction) 1 else 0)
  }.reduceByKey(_ + _)
    .collectAsMap()

  println(tpByClass)
  val tpSum = tpByClass.values.sum.toDouble
  println("tpSum:"+tpSum)

  val labelCountByClass: Map[Double, Long] = predictionAndLabels.values.countByValue()
  println(labelCountByClass)
  println("labelCountByClass.values.sum:"+labelCountByClass.values.sum)
}
