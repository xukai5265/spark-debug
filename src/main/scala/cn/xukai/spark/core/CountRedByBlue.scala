package cn.xukai.spark.core

import java.util.Properties

import org.apache.spark.sql.SparkSession

/**
  * 双色球 - 统计出 每个蓝球中红球的出现次数
  * Created by kaixu on 2018/1/17.
  */
object CountRedByBlue extends App {

  case class RawDataRecord(blue: String, red: String, count: Int)

  val spark = SparkSession.builder().master("local[2]").appName("demo5").getOrCreate()

  import spark.implicits._

  spark.sparkContext.setLogLevel("OFF")
  val url = "jdbc:mysql://localhost:3306/crawler-hx"
  val props = new Properties()
  props.put("url", url)
  props.put("driver", "com.mysql.jdbc.Driver")
  props.put("user", "root")
  props.put("password", "123456")
  val doubleBall = spark.read.jdbc(url, "double_ball", props)
  doubleBall.cache()
  doubleBall.createOrReplaceTempView("double_ball")

  val data01 = doubleBall.select("code", "red", "blue").where("blue=01")
  val data02 = doubleBall.select("code", "red", "blue").where("blue=02")
  val data03 = doubleBall.select("code", "red", "blue").where("blue=03")
  val data04 = doubleBall.select("code", "red", "blue").where("blue=04")
  val data05 = doubleBall.select("code", "red", "blue").where("blue=05")
  val data06 = doubleBall.select("code", "red", "blue").where("blue=06")
  val data07 = doubleBall.select("code", "red", "blue").where("blue=07")
  val data08 = doubleBall.select("code", "red", "blue").where("blue=08")
  val data09 = doubleBall.select("code", "red", "blue").where("blue=09")
  val data10 = doubleBall.select("code", "red", "blue").where("blue=10")
  val data11 = doubleBall.select("code", "red", "blue").where("blue=11")
  val data12 = doubleBall.select("code", "red", "blue").where("blue=12")
  val data13 = doubleBall.select("code", "red", "blue").where("blue=13")
  val data14 = doubleBall.select("code", "red", "blue").where("blue=14")
  val data15 = doubleBall.select("code", "red", "blue").where("blue=15")
  val data16 = doubleBall.select("code", "red", "blue").where("blue=16")

  val res01 = data01.select("red").rdd.flatMap { lines =>
    val line = lines.toString().replace("[", "").replace("]", "")
    println("line :" + line)
    line.split(",")
  }.map(x => (x, 1)).reduceByKey(_ + _).map(data =>
    RawDataRecord("01", data._1, data._2.toInt)
  ).toDF()
  val res02 = data02.select("red").rdd.flatMap { lines =>
    val line = lines.toString().replace("[", "").replace("]", "")
    println("line :" + line)
    line.split(",")
  }.map(x => (x, 1)).reduceByKey(_ + _).map(data =>
    RawDataRecord("02", data._1, data._2.toInt)
  ).toDF()
  val res03 = data03.select("red").rdd.flatMap { lines =>
    val line = lines.toString().replace("[", "").replace("]", "")
    println("line :" + line)
    line.split(",")
  }.map(x => (x, 1)).reduceByKey(_ + _).map(data =>
    RawDataRecord("03", data._1, data._2.toInt)
  ).toDF()
  val res04 = data04.select("red").rdd.flatMap { lines =>
    val line = lines.toString().replace("[", "").replace("]", "")
    println("line :" + line)
    line.split(",")
  }.map(x => (x, 1)).reduceByKey(_ + _).map(data =>
    RawDataRecord("04", data._1, data._2.toInt)
  ).toDF()
  val res05 = data05.select("red").rdd.flatMap { lines =>
    val line = lines.toString().replace("[", "").replace("]", "")
    println("line :" + line)
    line.split(",")
  }.map(x => (x, 1)).reduceByKey(_ + _).map(data =>
    RawDataRecord("05", data._1, data._2.toInt)
  ).toDF()
  val res06 = data06.select("red").rdd.flatMap { lines =>
    val line = lines.toString().replace("[", "").replace("]", "")
    println("line :" + line)
    line.split(",")
  }.map(x => (x, 1)).reduceByKey(_ + _).map(data =>
    RawDataRecord("06", data._1, data._2.toInt)
  ).toDF()
  val res07 = data07.select("red").rdd.flatMap { lines =>
    val line = lines.toString().replace("[", "").replace("]", "")
    println("line :" + line)
    line.split(",")
  }.map(x => (x, 1)).reduceByKey(_ + _).map(data =>
    RawDataRecord("07", data._1, data._2.toInt)
  ).toDF()
  val res08 = data08.select("red").rdd.flatMap { lines =>
    val line = lines.toString().replace("[", "").replace("]", "")
    println("line :" + line)
    line.split(",")
  }.map(x => (x, 1)).reduceByKey(_ + _).map(data =>
    RawDataRecord("08", data._1, data._2.toInt)
  ).toDF()
  val res09 = data09.select("red").rdd.flatMap { lines =>
    val line = lines.toString().replace("[", "").replace("]", "")
    println("line :" + line)
    line.split(",")
  }.map(x => (x, 1)).reduceByKey(_ + _).map(data =>
    RawDataRecord("09", data._1, data._2.toInt)
  ).toDF()
  val res10 = data10.select("red").rdd.flatMap { lines =>
    val line = lines.toString().replace("[", "").replace("]", "")
    println("line :" + line)
    line.split(",")
  }.map(x => (x, 1)).reduceByKey(_ + _).map(data =>
    RawDataRecord("10", data._1, data._2.toInt)
  ).toDF()
  val res11 = data11.select("red").rdd.flatMap { lines =>
    val line = lines.toString().replace("[", "").replace("]", "")
    println("line :" + line)
    line.split(",")
  }.map(x => (x, 1)).reduceByKey(_ + _).map(data =>
    RawDataRecord("11", data._1, data._2.toInt)
  ).toDF()
  val res12 = data12.select("red").rdd.flatMap { lines =>
    val line = lines.toString().replace("[", "").replace("]", "")
    println("line :" + line)
    line.split(",")
  }.map(x => (x, 1)).reduceByKey(_ + _).map(data =>
    RawDataRecord("12", data._1, data._2.toInt)
  ).toDF()
  val res13 = data13.select("red").rdd.flatMap { lines =>
    val line = lines.toString().replace("[", "").replace("]", "")
    println("line :" + line)
    line.split(",")
  }.map(x => (x, 1)).reduceByKey(_ + _).map(data =>
    RawDataRecord("13", data._1, data._2.toInt)
  ).toDF()
  val res14 = data14.select("red").rdd.flatMap { lines =>
    val line = lines.toString().replace("[", "").replace("]", "")
    println("line :" + line)
    line.split(",")
  }.map(x => (x, 1)).reduceByKey(_ + _).map(data =>
    RawDataRecord("14", data._1, data._2.toInt)
  ).toDF()
  val res15 = data15.select("red").rdd.flatMap { lines =>
    val line = lines.toString().replace("[", "").replace("]", "")
    println("line :" + line)
    line.split(",")
  }.map(x => (x, 1)).reduceByKey(_ + _).map(data =>
    RawDataRecord("15", data._1, data._2.toInt)
  ).toDF()
  val res16 = data16.select("red").rdd.flatMap { lines =>
    val line = lines.toString().replace("[", "").replace("]", "")
    println("line :" + line)
    line.split(",")
  }.map(x => (x, 1)).reduceByKey(_ + _).map(data =>
    RawDataRecord("16", data._1, data._2.toInt)
  ).toDF()

  val res_table = res01.union(res02).union(res03).union(res04).union(res05).union(res06)
    .union(res07).union(res08).union(res09).union(res10).union(res11).union(res12).union(res13)
    .union(res14).union(res15).union(res16)
  res_table.show(100)

  res_table.write.mode("append").jdbc(url, "double_ball_blue_red_relations", props)

  spark.close()

}
