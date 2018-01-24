package cn.xukai.spark.ml.mlp

import org.ansj.splitWord.analysis.ToAnalysis
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StopWordsRemover, StringIndexer, Word2Vec}
import org.apache.spark.sql.{SaveMode, SparkSession}

import scala.collection.mutable.ArrayBuffer

/**
  * 多层感知 - 文本分类器
  * 一、本文参考地址：
  *   代码：https://www.ibm.com/developerworks/cn/opensource/os-cn-spark-practice6/
  *   神经网络介绍：http://blog.csdn.net/aws3217150/article/details/46316007
  * 二、Spark 的多层感知器分类器 (MultilayerPerceptronClassifer) 支持以下可调参数:
  *1. featuresCol:输入数据 DataFrame 中指标特征列的名称。
  *2. labelCol：输入数据 DataFrame 中标签列的名称。
  *3. layers:这个参数是一个整型数组类型，第一个元素需要和特征向量的维度相等，最后一个元素需要训练数据的标签取值个数相等，如 2 分类问题就写 2。中间的元素有多少个就代表神经网络有多少个隐层，元素的取值代表了该层的神经元的个数。例如val layers = Array[Int](100,6,5,2)。
  *4. maxIter：优化算法求解的最大迭代次数。默认值是 100。
  *5. predictionCol:预测结果的列名称。
  *6. tol:优化算法迭代求解过程的收敛阀值。默认值是 1e-4。不能为负数。
  *7. blockSize:该参数被前馈网络训练器用来将训练样本数据的每个分区都按照 blockSize 大小分成不同组，并且每个组内的每个样本都会被叠加成一个向量，以便于在各种优化算法间传递。该参数的推荐值是 10-1000，默认值是 128。
  * 三、算法的返回是一个 MultilayerPerceptronClassificationModel 类实例。
  * Created by kaixu on 2018/1/23.
  */
object MLPNewsDemo extends App{
  final val VECTOR_SIZE = 1000
  val array = new ArrayBuffer[String](1894)
  val spark = SparkSession.builder().master("local[*]").appName("Word2VecDemo").getOrCreate()
  import spark.implicits._
  val sc = spark.sparkContext
  sc.setLogLevel("OFF")
  val url = "jdbc:postgresql://10.167.202.177:5432/crawler-hx"
  val prop = new java.util.Properties
  prop.put("user","TXDB")
  prop.put("password","123456")
  prop.put("driver","org.postgresql.Driver")
  val test = spark.read.jdbc(url,"test_tieba3_copy",prop)
  test.show(false)
  test.createOrReplaceTempView("test")
  val trainData = spark.sql("select label,content from test")
  // 负面
//  val positiveData = spark.sql("select label,content from test where label ='0'")
//  positiveData.printSchema()
//  println("负面数据条数："+positiveData.count())
//  // 正面
//  val negativeData = spark.sql("select label,content from test where label ='1'")
//  println("正面数据条数："+negativeData.count())
//  val trainData = positiveData.union(negativeData)

  val dataDF = trainData.map{ line =>
    (line.getString(0), ToAnalysis.parse(line.getString(1)).toStringWithOutNature(" ").split(" "))
  }.toDF("label","text")

  sc.textFile("stopword.txt",1).map(line => array+=(line)).collect()
  val stopwords = array.toArray
  println("停词数量："+stopwords.size)


  val labelIndexer = new StringIndexer()
    .setInputCol("label")
    .setOutputCol("indexedLabel")
    .fit(dataDF)
  // 移除停词
  val removers = new StopWordsRemover().setInputCol("text").setOutputCol("filtered").setStopWords(stopwords)
  val word2Vec = new Word2Vec()
    .setInputCol("filtered")
    .setOutputCol("features")
    .setVectorSize(VECTOR_SIZE)
    .setMinCount(1)

  val layers = Array[Int](VECTOR_SIZE,6,5,3)
  val mlpc = new MultilayerPerceptronClassifier()
    .setLayers(layers)
    .setBlockSize(512)
    .setSeed(1234L)
    .setMaxIter(128)
    .setFeaturesCol("features")
    .setLabelCol("indexedLabel")
    .setPredictionCol("prediction")

  val labelConverter = new IndexToString()
    .setInputCol("prediction")
    .setOutputCol("predictedLabel")
    .setLabels(labelIndexer.labels)

  val Array(trainingData, testData) = dataDF.randomSplit(Array(0.8, 0.2))

  val pipeline = new Pipeline().setStages(Array(labelIndexer,removers,word2Vec,mlpc,labelConverter))
  val model = pipeline.fit(trainingData)

  val predictionResultDF = model.transform(testData)
  //below 2 lines are for debug use
  predictionResultDF.printSchema
  predictionResultDF.select("label","predictedLabel","prediction").show(30,false)
  val saveOptions = Map("header" -> "true", "path" -> "mlp")
  predictionResultDF.select("label","predictedLabel","prediction").repartition(1).write.format("com.databricks.spark.csv").mode(SaveMode.Overwrite).options(saveOptions).save()

  val evaluator = new MulticlassClassificationEvaluator()
    .setLabelCol("indexedLabel")
    .setPredictionCol("prediction")
    .setMetricName("accuracy")
  val predictionAccuracy = evaluator.evaluate(predictionResultDF)
  println("Testing Accuracy is %2.4f".format(predictionAccuracy * 100) + "%")
  spark.stop

}
