package cn.xukai.spark.ml
import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.feature.{HashingTF, IDF, Tokenizer}
import org.apache.spark.mllib.classification.NaiveBayes
import org.apache.spark.ml.linalg.Vector
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.sql.{Row, SparkSession}

/**
  *  val regexTokenizer = new RegexTokenizer()
    .setInputCol("sentence")
    .setOutputCol("words")
    .setPattern("\\W") // alternatively .setPattern("\\w+").setGaps(false)

  val regexTokenized = regexTokenizer.transform(sentenceDataFrame)
  println("output-2")
  regexTokenized.select("words", "label").take(4).foreach(println)
  */

/**
  * Created by kaixu on 2017/8/29.
  */
object TokenizeDemo extends App{
  case class RawDataRecord(label: Int, text: String)
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  val spark = SparkSession.builder().master("spark://192.168.107.128:7077").appName("TokenizeDemo").getOrCreate()
  val sc = spark.sparkContext
  import spark.implicits._
  val parsedData  =  sc.textFile("file:///application/spark-2.2.0-bin-hadoop2.7/examples/jars/sougou").map {
    x =>
      val data = x.split(",")
      RawDataRecord(data(0).toInt,data(1))
  }
  val srcRDD = parsedData.toDF()
  val splits = srcRDD.randomSplit(Array(0.7, 0.3))
  var trainingDF = splits(0).toDF()
  var testDF = splits(1).toDF()

  var tokenizer = new Tokenizer().setInputCol("text").setOutputCol("words")
  var wordsData = tokenizer.transform(trainingDF)
  println("output1：")
  wordsData.select($"label",$"text",$"words").take(1).foreach(println)

  //计算每个词在文档中的词频
  var hashingTF = new HashingTF().setNumFeatures(500000).setInputCol("words").setOutputCol("rawFeatures")
  var featurizedData = hashingTF.transform(wordsData)
  println("output2：")
  featurizedData.select($"label", $"words", $"rawFeatures").take(1).foreach(println)

  //计算每个词的TF-IDF
  var idf = new IDF().setInputCol("rawFeatures").setOutputCol("features")
  var idfModel = idf.fit(featurizedData)
  var rescaledData = idfModel.transform(featurizedData)
  println("output3：")

  var trainDataRdd = rescaledData.select($"label",$"features").rdd.map{
    case Row(label: String, features: Vector) =>
      LabeledPoint(label.toDouble, Vectors.dense(features.toArray))
  }

//
//  val arr = new Array[Double](10)
//  arr(0)=1
//  arr(1)=2
//  arr(2)=3
//  val rdd = sc.parallelize(Seq(LabeledPoint(0,Vectors.dense(arr))))
  MLUtils.saveAsLibSVMFile(trainDataRdd,"file:///application/spark-2.2.0-bin-hadoop2.7/examples/jars/sougoures")


}
