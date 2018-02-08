//package cn.xukai.spark.ml.logisiticRegression
//import breeze.linalg.{DenseMatrix, DenseVector}
//import breeze.numerics.{log, sigmoid}
//
//import scala.collection.mutable
///**
//  * Created by kaixu on 2018/1/8.
//  */
//class LogisticRegressionModel() {
//  var learningRate:Double = _
//  var iterationTime:Int = _
//  var w: DenseVector[Double] = _
//  var b: Double = _
//  val costHistory: mutable.TreeMap[Int, Double] = new mutable.TreeMap[Int, Double]()
//
//  def setLearningRate(learningRate:Double):this.type ={
//    this.learningRate = learningRate
//    this
//  }
//  def setIterationTime(iterationTime:Int):this.type ={
//    this.iterationTime=iterationTime
//    this
//  }
//
//  def train(feature: DenseMatrix[Double], label: DenseVector[Double]): this.type = {
//
//    var (w, b) = initializeParams(feature.cols)
//
//    (1 to this.iterationTime)
//      .foreach{i =>
//        val (cost, dw, db) = propagate(feature, label, w, b)
//
//        if (i % 100 == 0) println("INFO: Cost in " + i + "th time of iteration: " + cost)
//        costHistory.put(i, cost)
//
//        val adjustedLearningRate = this.learningRate / (log(i/1000 + 1) + 1)
//        w :-= adjustedLearningRate * dw
//        b -= adjustedLearningRate * db
//      }
//
//    this.w = w
//    this.b = b
//    this
//  }
//  def predict(feature: DenseMatrix[Double]): DenseVector[Double] = {
//
//    val yPredicted = sigmoid(feature * this.w).map{eachY =>
//      if (eachY <= 0.05) 0.0 else 1.0
//    }
//
//    yPredicted
//  }
//
//  def accuracy(label: DenseVector[Double], labelPredicted: DenseVector[Double]): Double = {
//    val numCorrect = (0 until label.length)
//      .map{index =>
//        if (label(index) == labelPredicted(index)) 1 else 0
//      }
//      .count(_ == 1)
//    numCorrect.toDouble / label.length.toDouble
//  }
//
//  def getCostHistory: mutable.TreeMap[Int, Double] = this.costHistory
//  def getLearningRate: Double = this.learningRate
//  def getIterationTime: Int = this.iterationTime
//
//  private def initializeParams(featureSize: Int): (DenseVector[Double], Double) = {
//    val w = DenseVector.rand[Double](featureSize)
//    val b = DenseVector.rand[Double](1).data(0)
//    (w, b)
//  }
//
//  private def propagate(feature: DenseMatrix[Double], label: DenseVector[Double], w: DenseVector[Double], b: Double): (Double, DenseVector[Double], Double) = {
//    val numExamples = feature.rows
//    val labelHat = sigmoid(feature * w + b)
//
//    //    println("DEBUG: feature * w + b is " + feature * w + b)
//    //    println("DEBUG: the feature's number of cols is " + feature.cols)
//    //    println("DEBUG: the feature's number of rows is " + feature.rows)
//    //    println("DEBUG: the labelHat is " + labelHat)
//
//    val cost = -(label.t * log(labelHat) + (DenseVector.ones[Double](numExamples) - label).t * log(DenseVector.ones[Double](numExamples) - labelHat)) / numExamples
//
//    val dw = feature.t * (labelHat - label) /:/ numExamples.toDouble
//    val db = DenseVector.ones[Double](numExamples).t * (labelHat - label) / numExamples.toDouble
//
//    //    println("DEBUG: the (dw, db) is " + dw + ", " + db)
//
//    (cost, dw, db)
//  }
//}
