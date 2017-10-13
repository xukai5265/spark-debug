package cn.xukai.spark.ml


import org.apache.spark.mllib.classification.NaiveBayes
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by kaixu on 2017/8/21.
  * http://www.jianshu.com/p/a88d4356cf22
  *
  * https://msdn.microsoft.com/magazine/mt694087
  *
  */
object bayes1 extends App{
  val conf = new SparkConf()
  conf.setAppName("nbayes 1")

  val sc = new SparkContext(conf)
  // Spark 读取文本文件
  val rawtxt = sc.textFile("/kaixu/human-body-features.txt")

  // 将文本文件的内容转化为我们需要的数据结构 LabeledPoint
  val allData = rawtxt.map {
    line =>
      val colData = line.split(',')
      LabeledPoint(colData(0).toDouble,Vectors.dense(colData(1).split(' ').map(_.toDouble)))
  }

  // 训练
  val nbTrained = NaiveBayes.train(allData)

  // 待分类的特征集合
  val txt = "6 130 8"
  val vec = Vectors.dense(txt.split(' ').map(_.toDouble))

  // 预测（分类）
  val nbPredict = nbTrained.predict(vec)

  println("预测此人性别是：" + (if(nbPredict == 0) "女" else "男"))
}
