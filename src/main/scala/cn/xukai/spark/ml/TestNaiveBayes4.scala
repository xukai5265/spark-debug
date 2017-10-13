package cn.xukai.spark.ml

import cn.xukai.spark.ml.TestNaiveBayes3.{predictions, _}
import org.apache.spark.SparkConf
import org.apache.spark.ml.classification.NaiveBayes
import org.apache.spark.ml.feature.{HashingTF, IDF, LabeledPoint, Tokenizer}
import org.apache.spark.ml.linalg.Vector
import org.apache.spark.sql.{Row, SparkSession}

/**
  * Created by kaixu on 2017/10/13.
  * 用于联系测试数据
  */
object TestNaiveBayes4 extends App{
  case class RawDataRecord(category: String, text: String)
  val conf = new SparkConf().setMaster("local[2]")
  conf.set("spark.sql.warehouse.dir", "file:/D:/spark/warehouse")
  conf.set("spark.sql.shuffle.partitions", "20")
  val spark = SparkSession.builder().appName("NaiveBayesTest4").config(conf).getOrCreate()
  spark.sparkContext.setLogLevel("WARN")
  val srcData = spark.sparkContext.textFile("file:/D:/spark/warehouse/trainData.txt").map{
    x =>
      val data = x.split(",")
      RawDataRecord(data(0),data(1))
  }
  import spark.implicits._
  val trainingData = srcData.toDF()
  //将词语转换成数组
  var tokenizer = new Tokenizer().setInputCol("text").setOutputCol("words")
  var wordsData = tokenizer.transform(trainingData)
  println("output1：")
  wordsData.select($"category",$"text").show(1,false)
  wordsData.select($"category",$"words").show(1,false)
  //计算每个词在文档中的词频
  var hashingTF = new HashingTF().setNumFeatures(500000).setInputCol("words").setOutputCol("rawFeatures")
  var featurizedData = hashingTF.transform(wordsData)
  println("output2：")
  featurizedData.select($"category", $"words").show(1,false)
  featurizedData.select($"category", $"rawFeatures").show(1,false)
  //计算每个词的TF-IDF
  var idf = new IDF().setInputCol("rawFeatures").setOutputCol("features")
  var idfModel = idf.fit(featurizedData)
  var rescaledData = idfModel.transform(featurizedData)
  println("output3：")
  rescaledData.select($"category", $"features").show(1,false)

  //训练数据
  var trainDataRdd = rescaledData.select($"category",$"features").map {
    case Row(label: String, features: Vector) => LabeledPoint(label.toDouble,features)
  }
  val bayes = new NaiveBayes()
  val bayesModel = bayes.fit(trainDataRdd)
  val predictions = bayesModel.transform(trainDataRdd)
  println("output4: ")
  predictions.show(10,false)


}
