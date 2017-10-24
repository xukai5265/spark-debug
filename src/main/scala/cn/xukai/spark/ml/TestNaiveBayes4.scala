package cn.xukai.spark.ml
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
  //获取测试数据
  val testData = spark.sparkContext.textFile("file:/D:/spark/warehouse/testData.txt");
  val testingData = testData.toDF("text")
  var wordsTestData = tokenizer.transform(testingData)

  println("output4: ")
  wordsTestData.select($"text").show(1,false)
  wordsTestData.select($"words").show(1,false)

  var testFeaturizedData = hashingTF.transform(wordsTestData)
  println("output5：")
  testFeaturizedData.select($"words").show(1,false)
  testFeaturizedData.select($"rawFeatures").show(1,false)

  var testIdfModel = idf.fit(testFeaturizedData)
  var testRescaledData = idfModel.transform(testFeaturizedData)
  println("output6：")
  rescaledData.select($"features").show(1,false)



  val bayesModel = bayes.fit(trainDataRdd)
  bayesModel.setPredictionCol("category")
  val predictions = bayesModel.transform(testRescaledData)
  println("output7：")
  predictions.printSchema()
  predictions.select($"text",$"rawPrediction",$"probability",$"category").show(20,false)



}
