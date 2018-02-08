package cn.xukai.spark.ml.featuresExtracting.ngram

import org.apache.spark.ml.feature.NGram
import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2018/1/23.
  */
object NgramDemo extends App{
  val spark = SparkSession.builder().master("local[*]").appName("n-gramDemo").getOrCreate()
  val sc = spark.sparkContext
  sc.setLogLevel("OFF")
  val wordDataFrame = spark.createDataFrame(Seq(
    (0, Array("Hi", "I", "heard", "about", "Spark")),
    (1, Array("I", "wish", "Java", "could", "use", "case", "classes")),
    (2, Array("Logistic", "regression", "models", "are", "neat"))
  )).toDF("label", "words")

  val ngram = new NGram().setInputCol("words").setOutputCol("ngrams")
  val ngramDataFrame = ngram.transform(wordDataFrame)
  ngramDataFrame.take(3).map(_.getAs[Stream[String]]("ngrams").toList).foreach(println)
}
