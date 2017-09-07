package cn.xukai.spark.ml

import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.classification.NaiveBayes
import org.apache.spark.ml.feature.{HashingTF, IDF, LabeledPoint, Tokenizer}
import org.apache.spark.ml.linalg.Vector
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.sql.{Row, SparkSession}

/**
  * Created by kaixu on 2017/8/28.
  * http://lxw1234.com/archives/2016/01/605.htm
  * bloom Filter http://blog.csdn.net/jiaomeng/article/details/1495500
  *
  * NaiveBayes
  */
object TestNaiveBayes3 extends App{

  case class RawDataRecord(label: Int, text: String)
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  val spark = SparkSession.builder().master("local[3]").appName("TestNaiveBayes3").getOrCreate()
  val sc = spark.sparkContext
  val sentenceDataFrame =  sc.textFile("trainData.txt").map {
    x =>
      val data = x.split(",")
      data.init
      RawDataRecord(data(0).toInt, data(1))
  }
  import spark.implicits._
  val srcRDD = sentenceDataFrame.toDF()
  //70%作为训练数据，30%作为测试数据
  val splits = srcRDD.randomSplit(Array(0.7, 0.3))
  var trainingDF = splits(0).toDF()
  var testDF = splits(1).toDF()
  println("srcRDD: "+srcRDD.count()+" trainingDF: "+trainingDF.count() )
  //将词语转换成数组
  var tokenizer = new Tokenizer().setInputCol("text").setOutputCol("words")
  var wordsData = tokenizer.transform(trainingDF)
  println("output1：")
  wordsData.select($"label",$"text",$"words").take(1)
  //计算每个词在文档中的词频
  var hashingTF = new HashingTF().setNumFeatures(500000).setInputCol("words").setOutputCol("rawFeatures")
  var featurizedData = hashingTF.transform(wordsData)
  println("output2：")
  featurizedData.select($"label", $"words", $"rawFeatures").take(1)

  //计算每个词的TF-IDF
  var idf = new IDF().setInputCol("rawFeatures").setOutputCol("features")
  var idfModel = idf.fit(featurizedData)
  var rescaledData = idfModel.transform(featurizedData)
  println("output3：")
  rescaledData.select($"label", $"features").show(1)

  //转换成Bayes的输入格式
//  var trainDataRdd = rescaledData.select($"label",$"features").map {
//    case Row(label: String, features: Vector) =>
//      LabeledPoint(label.toDouble, Vectors.dense(features.toArray))
//  }


  var trainDataRdd = rescaledData.select($"label",$"features").map {
    case Row(label: String, features: Vector) =>
      LabeledPoint(label.toDouble, Vectors.dense(features.toArray))
  }

  println("output4：")
  trainDataRdd.take(1)
//  //训练模型
  NaiveBayes.load("")
  val model = NaiveBayes.train(trainDataRdd.rdd, lambda = 1.0, modelType = "multinomial")
//
//  //测试数据集，做同样的特征表示及格式转换
//  var testwordsData = tokenizer.transform(testDF)
//  var testfeaturizedData = hashingTF.transform(testwordsData)
//  var testrescaledData = idfModel.transform(testfeaturizedData)
//  var testDataRdd = testrescaledData.select($"label",$"features").map {
//    case Row(label: String, features: Vector) =>
//      LabeledPoint(label.toDouble, Vectors.dense(features.toArray))
//  }
//
//  //对测试数据集使用训练模型进行分类预测
//  val testpredictionAndLabel = testDataRdd.map(p => (model.predict(p.features), p.label))
//
//  //统计分类准确率
//  var testaccuracy = 1.0 * testpredictionAndLabel.filter(x => x._1 == x._2).count() / testDataRdd.count()
//  println("output5：")
//  println(testaccuracy)



}
