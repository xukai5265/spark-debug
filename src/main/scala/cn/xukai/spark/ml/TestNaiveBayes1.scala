package cn.xukai.spark.ml

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.classification.NaiveBayes
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint

/**
  * Created by kaixu on 2017/8/28.
  */
object TestNaiveBayes1 {
  def main(args: Array[String]) {
    Vectors.dense(1)
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    val conf =new SparkConf()
    conf.setMaster("spark://192.168.107.128:7077").setAppName("TestNaiveBayes")
    val sc =new SparkContext(conf)
    val parsedData = sc.textFile("file:///application/spark-2.2.0-bin-hadoop2.7/examples/jars/sougou").map {
      x =>
        val data = x.split(",")
        LabeledPoint(data(0).toDouble,Vectors.dense(data(1).split(' ').map(_.toDouble)))
    }
    // 把数据的60%作为训练集，40%作为测试集.
    val splits = parsedData.randomSplit(Array(0.6,0.4),seed = 11L)
    val training =splits(0)
    val test =splits(1)
    //获得训练模型,第一个参数为数据，第二个参数为平滑参数，默认为1，可改
    val model = NaiveBayes.train(training)

    //对模型进行准确度分析
    val predictionAndLabel= test.map(p => (model.predict(p.features),p.label))
    val accuracy =1.0 *predictionAndLabel.filter(x => x._1 == x._2).count() / test.count()

    println("accuracy-->"+accuracy)
    println("Predictionof (0.0, 2.0, 0.0, 1.0):"+model.predict(Vectors.dense(0.0,2.0,0.0,1.0)))

  }
}
