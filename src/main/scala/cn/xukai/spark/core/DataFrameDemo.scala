package cn.xukai.spark.core

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.types.{ DataTypes, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}

/**
  * Created by kaixu on 2017/9/5.
  */
object DataFrameDemo extends App{
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  val spark = SparkSession.builder().appName("DataFrameDemo")
    .master("spark://192.168.107.128:7077").getOrCreate()
  spark.sparkContext.addJar("D:\\workspace\\sparkutil\\target\\spark.util-1.0-SNAPSHOT.jar")
  //创建dataframe
  val sc = spark.sparkContext
  val sqlContext = spark.sqlContext
  val idAgeRDDRow = sc.parallelize(Array(Row(1,20),Row(2,30),Row(3,40)))
  val schema = StructType(Array(StructField("id",DataTypes.IntegerType),StructField("age",DataTypes.IntegerType)))
  val idAgeDF = sqlContext.createDataFrame(idAgeRDDRow,schema)
  println("1 -> ")
  idAgeDF.show()
  println("2 -> ")
  idAgeDF.filter(idAgeDF.col("age") > 25).show()
  println("3 -> ")
  // 目前支持String, Integer, Long等类型直接创建Dataset
  import spark.implicits._
  Seq(1,2,3).toDS().show()
  Seq("a","b","c").toDS().show()
  case class Person(name: String, age: Long)
  Seq(Person("xukai",28),Person("zhangsan",20)).toDS().show()
  spark.close()
}
