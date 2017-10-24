package cn.xukai.spark.ml
import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.classification.NaiveBayes
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{HashingTF, IDF, LabeledPoint, Tokenizer}
import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2017/9/5.
  */
object TestNaiveBayes2 extends App{
  case class RawDataRecord(label: String, text: String)

  Logger.getLogger("org").setLevel(Level.WARN)
  val spark = SparkSession.builder().master("local[*]").appName("TokenizeDemo").getOrCreate()
  val url = "jdbc:postgresql://10.167.202.177:5432/crawler-hx"
  val prop = new java.util.Properties
  prop.put("user","TXDB")
  prop.put("password","123456")
  prop.put("driver","org.postgresql.Driver")
  val test = spark.read.jdbc(url,"test_tieba3",prop)
  test.show(false)
  import spark.implicits._
  test.createOrReplaceTempView("test")
  // 负面
  val positiveData = spark.sql("select label,content from test where label ='0'")
  positiveData.printSchema()
  println("负面数据条数："+positiveData.count())
  // 正面
  val negativeData = spark.sql("select label,content from test where label ='1'")
  println("正面数据条数："+negativeData.count())


  // 测试数据
//  val testData = spark.sql("select content from test where label is null")
//  println("测试数据条数："+testData.count())
  val trainData = positiveData.union(negativeData)

  val sentenceDataFrame = trainData.map{ data =>
    RawDataRecord(data.getString(0),data.getString(1))
  }

  val tokenizer = new Tokenizer().setInputCol("text").setOutputCol("words")
  var wordsData = tokenizer.transform(sentenceDataFrame)
  val hashingtf = new HashingTF().setInputCol(tokenizer.getOutputCol).setOutputCol("features")
  var featurizedData = hashingtf.transform(wordsData)
  val idf = new IDF().setInputCol(hashingtf.getOutputCol).setOutputCol("idf-features")
  var idfModel = idf.fit(featurizedData)
  var rescaledData = idfModel.transform(featurizedData)
  var trainDataRdd = rescaledData.select($"label",$"features").map { x =>
    LabeledPoint(x.getString(0).toDouble,x.getAs(1))
  }

  val Array(trainingData, testData) = trainDataRdd.randomSplit(Array(0.7, 0.3), seed = 1234L)
  val nb = new NaiveBayes()
  val nbModel = nb.fit(trainingData)
  val predictions = nbModel.transform(testData)
  /**
    * 准确率
    */
  val accuracy = 1.0*predictions.filter($"label"===$"prediction").count() / testData.count()
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
  val precision = 1.0 * tp / testData.count()
  println("precision-->"+precision)

  /**
    * 召回率
    */
  // 把原来的正类预测为负类
  val fn = predictions.filter($"prediction"===0.0).filter($"label"===1.0).count()
  println("FN ==="+fn)
  val recall = 1.0 * tp / testData.filter($"label"===1.0).count()
  println("recall ---> "+recall)


  val evaluator_accuracy = new MulticlassClassificationEvaluator()
    .setLabelCol("label")
    .setPredictionCol("prediction")
    .setMetricName("accuracy")
  val accuracy_spark = evaluator_accuracy.evaluate(predictions)
  println("Test set accuracy_spark = " + accuracy_spark)

  val evaluator_precision = new MulticlassClassificationEvaluator()
    .setLabelCol("label")
    .setPredictionCol("prediction")
    .setMetricName("weightedPrecision")
  val precision_spark = evaluator_precision.evaluate(predictions)
  println("Test set weightedPrecision_spark = " + precision_spark)

  val evaluator_recall = new MulticlassClassificationEvaluator()
    .setLabelCol("label")
    .setPredictionCol("prediction")
    .setMetricName("weightedRecall")
  val recall_spark = evaluator_recall.evaluate(predictions)
  println("Test set weightedRecall_spark = " + recall_spark)

  val evaluator_f1 = new MulticlassClassificationEvaluator()
    .setLabelCol("label")
    .setPredictionCol("prediction")
    .setMetricName("f1")
  val f1_spark = evaluator_f1.evaluate(predictions)
  println("Test set f1_spark = " + f1_spark)


//  positiveData.map( data => AnsjUtils.getFC(data))
  predictions.show(20,false)
  predictions.printSchema()

  spark.close()
}
