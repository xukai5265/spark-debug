package cn.xukai.spark.ml.sgd

import java.io.PrintWriter

import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.{LabeledPoint, LinearRegressionWithSGD}
import org.apache.spark.sql.SparkSession

import scala.util.Random

/**
  * Created by kaixu on 2018/1/30.
  */
object SGD1 {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.eclipse.jetty.server").setLevel(Level.OFF)

  //产生我们需要的数据
  def makedata() {
    // y=3x+4y+2.5z+g
    var o = new PrintWriter("D:\\test_data\\spark\\sgd\\hei")
    val d = 1
    for (j <- 1 until 20) {
      var x = (new Random).nextDouble() * d
      var y = (new Random).nextDouble() * d
      var z = (new Random).nextDouble() * d
      var g = (new Random).nextDouble() * d
      o.println((3 * x + 4 * y + 2.5 * z + g) + "," + x + " " + y + " " + z + " " + g)
    }
    o.close()
  }

  def main(args: Array[String]): Unit = {
//    makedata()

    val spark = SparkSession.builder().master("local[*]").appName("SGD").getOrCreate()
    val sc = spark.sparkContext
    val data = sc.textFile("D:\\test_data\\spark\\sgd\\hei").map { x =>
      val m = x.split(",")
      LabeledPoint(m(0).toDouble, Vectors.dense(m(1).split(" ").map {
        _.toDouble
      }))
    }
    //设置需要的参数
    val numIterations = 1000
    val stepSize = 1
    //训练模型
    val model = LinearRegressionWithSGD.train(data, numIterations, stepSize)
    //输出权重及偏置
    println(model.weights)
    println(model.intercept)
    //预测数据
    val predict = model.predict(data.map { x => x.features })
    val predictlable = predict.zip(data.map { x => x.label })
    predictlable.foreach(f => println(f._1 + "                " + f._2))
    println("次数："+predict.count().toDouble)
    //计算误差
    val loss = predictlable.map(f =>
      math.pow((f._1 - f._2), 2.0)
    ).reduce(_ + _) / predict.count().toDouble
    println(loss)
  }
}
