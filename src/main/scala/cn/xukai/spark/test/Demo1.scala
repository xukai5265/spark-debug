package cn.xukai.spark.test

import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2018/1/3.
  * a.txt 文本内容如下格式：
  * 1.求出所有字母出现的个数
  * 2.求出第四列字幕出现个数
  * A,b,c,d
    B,b,f,e
    A,a,c,f
    A,b,c,d
    A,b,c,d
  */
object Demo1 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("test").master("local[*]").getOrCreate()
    // 做法一：
    val sc = spark.sparkContext
    sc.setLogLevel("WARN")
    val text = sc.textFile("D:\\test_data\\spark\\a.txt")
    // 1
    text.flatMap(line => line.split(",")).map(x => (x, 1)).reduceByKey(_ + _).collect.foreach(println)
    Thread.sleep(100000000)
    // 2
//    text.map(line => line.split(",")).map( x => (x(3),1)).reduceByKey(_+_).collect.foreach(println)

    // 做法二：
//    val text1 = spark.read.textFile("D:\\test_data\\spark\\a.txt")
//    import spark.implicits._
//    val res2 = text1.map(line => line.split(",")).map(x => (x(0),x(1),x(2),x(3)))
//    res2.createOrReplaceTempView("text")
//    res2.show(false)
//    res2.printSchema()
//    spark.sql("select _4,count(_4) from text group by _4").show(false)
  }
}
