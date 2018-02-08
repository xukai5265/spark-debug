package cn.xukai.spark.core.double_ball

import java.util.Properties

import org.apache.spark.sql.SparkSession

import scala.collection.mutable.ArrayBuffer

/**
  * 计算相邻两期双色球-红球重复的次数
  * Created by kaixu on 2018/1/26.
  */
object RepeatRedCount extends App {
  val arrayBuffer = new ArrayBuffer[Int]();
  val spark = SparkSession.builder().master("local[*]").appName("RepeatRedCount").getOrCreate()
  spark.sparkContext.setLogLevel("OFF")
  import spark.implicits._
  val url = "jdbc:mysql://localhost:3306/crawler-hx"
  val props = new Properties()
  props.put("url", url)
  props.put("driver", "com.mysql.jdbc.Driver")
  props.put("user", "root")
  props.put("password", "123456")
  val doubleBall = spark.read.jdbc(url, "double_ball", props)
  doubleBall.cache()
  doubleBall.createOrReplaceTempView("double_ball")

  val reds = spark.sql("select red from double_ball order by code desc")
  println(s"partitions size：${reds.rdd.partitions.size}")

  reds.map(red => red.getString(0).split(",").toList).reduce(compute(_,_))

  val finalRes = arrayBuffer.groupBy(a => a).map{ line =>
    val key = line._1
    val value = line._2.size
    (key,value)
  }
  println("------------")
  println(finalRes)

  println("------------")
  val bz = finalRes.map(x => (x._1,x._2.toDouble/778))
  println(bz)
  println("------------")
  println(bz.map(x => x._2).sum)


  /*
   4 -> 2017074
   */

  def compute(list1:List[String],list2:List[String]): List[String] ={
    val res = list1.intersect(list2)
    if(res.size>=4){
      println("list1:"+list1)
      println("list2:"+list2)
      println("--------------------------")
    }
    arrayBuffer+=res.size
    list2
  }

}
