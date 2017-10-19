package cn.xukai.spark.ml
import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.classification.NaiveBayes
import org.apache.spark.ml.feature.{HashingTF, IDF, LabeledPoint, Tokenizer}
import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2017/8/28.
  * http://lxw1234.com/archives/2016/01/605.htm
  * bloom Filter http://blog.csdn.net/jiaomeng/article/details/1495500
  *
  * NaiveBayes
  */
object SougouNB extends App{

  case class RawDataRecord(label: Int, text: String)
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  val spark = SparkSession.builder().master("local[*]").appName("TokenizeDemo").getOrCreate()
  val sc = spark.sparkContext
  //  file:/D:/spark/warehouse/trainData.txt
  val sentenceDataFrame =  sc.textFile("D:\\资料\\语料\\sougou-train").map {
    x =>
      val data = x.split(",")
      data.init
      RawDataRecord(data(0).toInt, data(1))
  }
  import spark.implicits._
  val srcRDD = sentenceDataFrame.toDF()

  val tokenizer = new Tokenizer().setInputCol("text").setOutputCol("words")
  var wordsData = tokenizer.transform(srcRDD)

  val hashingtf = new HashingTF().setInputCol(tokenizer.getOutputCol).setOutputCol("features")
  var featurizedData = hashingtf.transform(wordsData)
  println("println 1")
  featurizedData.select($"label",$"words", $"features").show(10,false)

  // 计算每个次的idf值
  val idf = new IDF().setInputCol(hashingtf.getOutputCol).setOutputCol("idf-features")
  var idfModel = idf.fit(featurizedData)
  var rescaledData = idfModel.transform(featurizedData)

  println("println 2 ")
  featurizedData.select($"label",$"features").show(20,false)

  var trainDataRdd = rescaledData.select($"label",$"features").map { x =>
    LabeledPoint(x.getInt(0).toDouble,x.getAs(1))
  }

  val Array(trainingData, testData) = trainDataRdd.randomSplit(Array(0.7, 0.3), seed = 1234L)
  val nb = new NaiveBayes()
  val nbModel = nb.fit(trainingData)
  val predictions = nbModel.transform(testData)
  // 准确率
  val accuracy = 1.0*predictions.filter($"label"===$"prediction").count()/testData.count()
  println("accuracy-->"+accuracy)
  /**
    * 精确率
    */
  // 把正类 预测为正类
  val tp = predictions.filter($"label"===$"prediction").filter($"label"===1.0).count()
  println("tp==="+tp)
  // 把负类 预测为正类
  val fp = predictions.filter($"prediction"===1.0).filter($"label"===0.0).count()
  println("fp==="+fp)
  val precision = 1.0 * tp / (tp + fp)
  println("precision-->"+precision)
  /**
    * 召回率
    */
  // 把原来的正类预测为负类
  val FN = predictions.filter($"prediction"===0.0).filter($"label"===1.0).count()
  println("FN ==="+FN)
  val recall = 1.0 * tp / (tp+ FN)
  println("recall ---> "+recall)

  predictions.printSchema()
  predictions.show(10,false)
  //将rdd 结果 保存到文件
//  predictions.select($"label",$"rawPrediction",$"probability",$"prediction").rdd.repartition(1).saveAsTextFile("D:\\spark\\warehouse\\features")

}
