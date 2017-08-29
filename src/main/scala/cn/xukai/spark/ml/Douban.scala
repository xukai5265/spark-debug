//package cn.xukai.spark.ml
//
//import org.ansj.splitWord.analysis.BaseAnalysis
//import org.apache.spark.ml.feature.{HashingTF, IDF, Tokenizer}
//import org.apache.spark.sql.SparkSession
//
///**
//  * Created by kaixu on 2017/8/25.
//  *
//  * https://www.ibm.com/developerworks/cn/cognitive/library/cc-1606-spark-seniment-analysis/index.html
//  */
//object Douban {
//  def main(args: Array[String]): Unit = {
//
//    val spark = SparkSession.builder().master("").appName("").getOrCreate()
//    val sc = spark.sparkContext
//    val originData = spark.read.textFile("")
//    val originDistinctData = originData.distinct()
//    val rateDocument = originDistinctData.map( line => line.split("\t")).filter(line => line.length==2)
//    val fiveRateDocument = rateDocument.filter( line => line(0)==5)
//    println(fiveRateDocument.count())
//    val fourRateDocument = rateDocument.filter( line => line(0)==4)
//    println(fourRateDocument.count())
//    val threeRateDocument = rateDocument.filter( line => line(0)==3)
//    println(threeRateDocument.count())
//    val twoRateDocument = rateDocument.filter( line => line(0)==2)
//    println(twoRateDocument.count())
//    val oneRateDocument = rateDocument.filter( line => line(0)==1)
//    println(oneRateDocument.count())
//
//    val negRateDocument = oneRateDocument.union(twoRateDocument).union(threeRateDocument)
//    negRateDocument.repartition(1)
//    import spark.implicits._
//    val posRateDocument = sc.parallelize(fiveRateDocument.take(Integer.parseInt(negRateDocument.count().toString))).repartition(1).toDS()
//    val allRateDocumen = negRateDocument.union(posRateDocument)
//    allRateDocumen.repartition(1)
//    val rate = allRateDocumen.map( s => s(0))
//    val document = allRateDocumen.map( s => s(1))
//
//    // 中文分词（基础分词）
//    val words = document.map( word => BaseAnalysis.parse(word).toStringWithOutNature(" "))
//    val hashingTF = new HashingTF()
//    val tf = hashingTF.transform(words)
//    tf.cache()
//
//  }
//}
