package cn.xukai.spark.ml.featuresExtracting.stringIndexer

import org.apache.spark.ml.feature.StringIndexer
import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2018/1/5.
  */
object StringIndexerDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local").appName("StringIndexerTest").getOrCreate()
    spark.sparkContext.setLogLevel("WARN")
    val df = spark.createDataFrame(Seq((0,"a"),(1,"b"),(2,"c"),(3,"a"),(4,"a"),(5,"c"))).toDF("id","category")
    val categoryIndexer = new StringIndexer().setInputCol("category").setOutputCol("categoryIndex")
    val categoryIndexed = categoryIndexer.fit(df).transform(df)
    categoryIndexed.show()

    val df1 = spark.createDataFrame(Seq((0,"a"),(1,"b"),(2,"c"),(3,"a"),(4,"a"),(5,"d"))).toDF("id","category")
    val indexed1 = categoryIndexer.setHandleInvalid("skip").fit(df).transform(df1)
    indexed1.show()

    categoryIndexer.setHandleInvalid("keep").fit(df).transform(df1).show()

  }
}
