package cn.xukai.spark.ml.featuresExtracting.tokenizer

import org.apache.spark.ml.feature.{RegexTokenizer, Tokenizer}
import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2018/1/23.
  */
object TokenizerDemo extends App{
  val spark = SparkSession.builder().master("local[*]").appName("TokenizerDemo").getOrCreate()
  spark.sparkContext.setLogLevel("OFF")
  val sentenceDataFrame = spark.createDataFrame(Seq(
    (0, "Hi I heard about Spark"),
    (1, "I wish Java could use case classes"),
    (2, "Logistic,regression,models,are,neat")
  )).toDF("label", "sentence")

  val tokenizer = new Tokenizer().setInputCol("sentence").setOutputCol("words")
  val regexTokenizer = new RegexTokenizer()
    .setInputCol("sentence")
    .setOutputCol("words")
    .setPattern("\\W") // 参数："\\W" ---> 匹配字母或数字或下划线或汉字 等价于 '[A-Za-z0-9_]'。 alternatively .setPattern("\\w+").setGaps(false)

  val tokenized = tokenizer.transform(sentenceDataFrame)
  println("tokenizer println...")
  tokenized.select("words", "label").take(3).foreach(println)
  val regexTokenized = regexTokenizer.transform(sentenceDataFrame)
  println("regex tokenized println...")
  regexTokenized.select("words", "label").take(3).foreach(println)
}
