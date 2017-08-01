package cn.xukai.spark.streaming
import org.apache.log4j.{Level, Logger}
import org.apache.spark.streaming.Seconds
import org.apache.spark.SparkConf
import org.apache.spark.streaming.StreamingContext

/**
  * Created by kaixu on 2017/7/31.
  */
object FunDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("FunDemo").setMaster("local[*]")
    val ssc = new StreamingContext(conf,Seconds(5))
    val lines = ssc.socketTextStream("192.168.107.128",9999)
    /**
      * spark 日志输出级别控制
      */
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    // map 操作
//    val lineNew = lines.map(_ + "_new")
//    lineNew.print()

    // flatMap 操作
//    val words = lines.flatMap(_.split(" "))
//    words.print()

    //filter 操作
//    val res = lines.filter(!_.contains("ERROR"))
//    res.print()

    /*
      union
      val rdd1 = List(1,2,3,4)
      val rdd2 = List(4,5,6,7)
      val unionRdd = rdd1.union(rdd2)
      Array(1,2,3,4,4,5,6,7)
     */
//    val words = lines.flatMap(_.split(" "))
//    val wordOne = words.map(_ +"one")
//    val wordTwo = words.map(_ + "two")
//    val wordUnion = wordOne.union(wordTwo)
//    wordOne.print()
//    wordTwo.print()
//    wordUnion.print()

    // count 统计 rdd 包含元素的个数
//    val words = lines.flatMap(_.split(" "))
//    val wordsCount = words.count()
//    wordsCount.print()

    /*
    reduce
    输入：xukai wujie xuchengjun
    输出：xukai-wujie-xuchengjun
     */
//    val words = lines.flatMap(_.split(" "))
//    val reduceWords = words.reduce(_ + "-" + _)
//    reduceWords.print()

    /*
      countByValue
      输入：xukai wujie xukai
      输出：(xukai,2)
            (wujie,1)
     */
//    val words = lines.flatMap(_.split(" "))
//    val countByValueWords = words.countByValue()
//    countByValueWords.print()

    /*
        reduceByKey
        输入： xukai wujie xukai
        输出： (xukai,1)(xukai,1)(wujie,1)
     */
//    val words = lines.flatMap(_.split(" "))
////    val pairs1 = words.map(_ => (_,1))
//    val pairs2 = words.map(word => (word,1))
//    val wordsCount = pairs2.reduceByKey(_+_)
//    wordsCount.print()

    //join

    /*
        transform 对rdd 操作
      */
//    val words = lines.transform(rdd => rdd.flatMap(_.split(" ")))
//    words.print()
    val words = lines.flatMap(_.split(" "))
    val wordDstream = words.map(word => (word,1))


    ssc.start()
    ssc.awaitTermination()
  }
}
