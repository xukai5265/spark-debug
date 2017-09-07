package cn.xukai.spark.ml

import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.tree.DecisionTree
import org.apache.spark.mllib.tree.model.DecisionTreeModel
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2017/9/1.
  */
object DecisionTreeDemo extends App{
  val spark = SparkSession.builder().master("").appName("").getOrCreate()
  val sc = spark.sparkContext
  val rawData = sc.textFile("")
  val data = rawData.map{ line =>
    val values = line.split(",").map(_.toDouble)
    val featureVector = Vectors.dense(values.init)
    val label = values.last -1
    LabeledPoint(label,featureVector)
  }
  val Array(trainData,cvData,testData) = data.randomSplit(Array(0.8,0.1,0.1))
  trainData.cache()
  cvData.cache()
  testData.cache()

  def getMetrics(model:DecisionTreeModel,data:RDD[LabeledPoint]):MulticlassMetrics={
    val predictionsAndLables = data.map(example => (model.predict(example.features),example.label))
    new MulticlassMetrics(predictionsAndLables)
  }

  val model = DecisionTree.trainClassifier(trainData,7,Map[Int,Int](),"gini",4,100)
  val metrics = getMetrics(model,cvData)
  println(metrics.confusionMatrix)
}
