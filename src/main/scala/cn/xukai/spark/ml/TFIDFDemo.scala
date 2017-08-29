package cn.xukai.spark.ml

import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.feature.{HashingTF, IDF, Tokenizer}
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.sql.{Row, SparkSession}

/**
  * Created by kaixu on 2017/8/28.
  */


object TFIDFDemo {
  case class RawDataRecord(category: String, text: String)
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    val spark = SparkSession
      .builder()
      .appName("TF-IDF")
      .master("spark://192.168.107.128:7077")
      .getOrCreate()
    import spark.implicits._
    val data = spark.read.textFile("file:///application/spark-2.2.0-bin-hadoop2.7/examples/jars/1.txt")
    data.take(2).foreach(println)
    val srcDF = data.map{
      line =>
        val data_ = line.split(",")
        RawDataRecord(data_(0), data_(1))
    }
    srcDF.select("category", "text").take(2).foreach(println)
    val tokenizer = new Tokenizer().setInputCol("text").setOutputCol("words")
    val wordsData = tokenizer.transform(srcDF)
    wordsData.select("category", "text", "words").take(2).foreach(println)

    val hashingTF = new HashingTF().setInputCol("words").setOutputCol("rawFeatures").setNumFeatures(100)
    val featurizedData = hashingTF.transform(wordsData)
    featurizedData.select("category", "words", "rawFeatures").take(2).foreach(println)

    // 计算TF-IDF的值
    val idf = new IDF().setInputCol("rawFeatures").setOutputCol("features")
    val idfModel = idf.fit(featurizedData)
    val rescaledData = idfModel.transform(featurizedData)
    rescaledData.select($"category", $"words", $"features").take(2).foreach(println)

    val trainDataRdd = rescaledData.select($"category",$"features").map {
      case Row(label: String, features: Vector) =>
        LabeledPoint(label.toDouble, Vectors.dense(features.toArray))
    }
    trainDataRdd.select($"label",$"features").take(2).foreach(println)
  }
}
