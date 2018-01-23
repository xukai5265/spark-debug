package cn.xukai.spark.ml.mlp

import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StopWordsRemover, StringIndexer, Word2Vec}
import org.apache.spark.sql.SparkSession

import scala.collection.mutable.ArrayBuffer

/**
  * 多层感知 - 文本分类器
  * 本文参考地址：
  *   代码：https://www.ibm.com/developerworks/cn/opensource/os-cn-spark-practice6/
  *   神经网络介绍：http://blog.csdn.net/aws3217150/article/details/46316007
  * Created by kaixu on 2018/1/23.
  */
object MLPDemo extends App{
  final val VECTOR_SIZE = 3000
  val array = new ArrayBuffer[String]()
  val spark = SparkSession.builder().master("local[*]").appName("Word2VecDemo").getOrCreate()
  val sc = spark.sparkContext
  import spark.implicits._
  sc.setLogLevel("OFF")
  val srcRDD =  sc.textFile("trainData.txt")
  sc.textFile("stopword.txt").map(line => array+=(line))
  val stopwords = array.toArray
  val dataDF = srcRDD.map {
    x =>
      val data = x.split(",")
      (data(0).toInt, data(1).split(" "))
  }.toDF("label","text")

  val labelIndexer = new StringIndexer()
    .setInputCol("label")
    .setOutputCol("indexedLabel")
    .fit(dataDF)
  // 移除停词
  val removers = new StopWordsRemover().setInputCol("text").setOutputCol("filtered").setStopWords(stopwords)
  val word2Vec = new Word2Vec()
    .setInputCol("filtered")
    .setOutputCol("features")
    .setVectorSize(VECTOR_SIZE)
    .setMinCount(1)

  val layers = Array[Int](VECTOR_SIZE,6,5,2)
  val mlpc = new MultilayerPerceptronClassifier()
    .setLayers(layers)
    .setBlockSize(512)
    .setSeed(1234L)
    .setMaxIter(128)
    .setFeaturesCol("features")
    .setLabelCol("indexedLabel")
    .setPredictionCol("prediction")

  val labelConverter = new IndexToString()
    .setInputCol("prediction")
    .setOutputCol("predictedLabel")
    .setLabels(labelIndexer.labels)

  val Array(trainingData, testData) = dataDF.randomSplit(Array(0.8, 0.2))

  val pipeline = new Pipeline().setStages(Array(labelIndexer,removers,word2Vec,mlpc,labelConverter))
  val model = pipeline.fit(trainingData)

  val predictionResultDF = model.transform(testData)
  //below 2 lines are for debug use
  predictionResultDF.printSchema
  predictionResultDF.select("text","label","predictedLabel").show(30)

  val evaluator = new MulticlassClassificationEvaluator()
    .setLabelCol("indexedLabel")
    .setPredictionCol("prediction")
    .setMetricName("accuracy")
  val predictionAccuracy = evaluator.evaluate(predictionResultDF)
  println("Testing Accuracy is %2.4f".format(predictionAccuracy * 100) + "%")
  spark.stop

}
