//package cn.xukai.spark.ml.logisiticRegression
//import breeze.linalg.{DenseMatrix, DenseVector}
///**
//  * Created by kaixu on 2018/1/8.
//  */
//class DlCollection[E <: Cat](data: List[E])  {
//  private val numRows: Int = this.data.size
//  private val numCols: Int = this.data.head.feature.length
//
//  def split(trainingSize: Double): (DlCollection[E], DlCollection[E]) = {
//    val splited = data.splitAt((data.length * trainingSize).toInt)
//    (new DlCollection[E](splited._1), new DlCollection[E](splited._2))
//  }
//
//  def getFeatureAsMatrix: DenseMatrix[Double] = {
//    val feature = DenseMatrix.zeros[Double](this.numRows, this.numCols)
//
//    var i = 0
//    this.data.foreach{eachRow =>
//      feature(i, ::) := eachRow.feature.t
//      i = i+1
//    }
//
//    feature
//  }
//
//  def getLabelAsVector: DenseVector[Double] = {
//    val label = DenseVector.zeros[Double](this.numRows)
//
//    var i: Int = 0
//    this.data.foreach{eachRow =>
//      label(i) = eachRow.label
//      i += 1
//    }
//
//    label
//  }
//
//
//  override def toString = s"DlCollection($numRows, $numCols, $getFeatureAsMatrix, $getLabelAsVector)"
//}
