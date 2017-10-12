package cn.xukai.spark.dataframe

import org.apache.spark.SparkConf
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

/**
  * Created by kaixu on 2017/10/12.
  * 演示 Rdd 转换为 DataFrame对象
  */
object Rdd2DataFrame {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[2]")
    conf.set("spark.sql.warehouse.dir","file:/D:/spark/warehouse")
    conf.set("spark.sql.shuffle.partitions","20")
    val sparkSession = SparkSession.builder().appName("RDD to DataFrame")
      .config(conf).getOrCreate()
    //通过代码的方式,设置Spark log4j的级别
    sparkSession.sparkContext.setLogLevel("WARN")
    import sparkSession.implicits._

    //使用 case class 转换 RDD 到 DataFrame
//    val peopleDF = rddToDFCase(sparkSession)

    //使用 StructType  转换 RDD 到 DataFrame
    val peopleDF = rddToDF(sparkSession)
    peopleDF.show()
    peopleDF.select($"name",$"age").filter($"age">20).show()
  }

  //use case class Person
  case class Person(name:String,age:Int)
  def rddToDFCase(sparkSession : SparkSession):DataFrame = {
    //导入隐饰操作，否则RDD无法调用toDF方法
    import sparkSession.implicits._
    val peopleRDD = sparkSession.sparkContext
      .textFile("file:/D:/spark/warehouse/people.txt",2)
      .map( x => x.split(",")).map( x => Person(x(0),x(1).trim().toInt)).toDF()
    peopleRDD
  }

  //StructType and convert RDD to DataFrame
  def rddToDF(sparkSession : SparkSession):DataFrame = {
    //设置schema结构
    val schema = StructType(
      Seq(
        StructField("name",StringType,true)
        ,StructField("age",IntegerType,true)
      )
    )
    val rowRDD = sparkSession.sparkContext
      .textFile("file:/D:/spark/warehouse/people.txt",2)
      .map( x => x.split(",")).map( x => Row(x(0),x(1).trim().toInt))
    sparkSession.createDataFrame(rowRDD,schema)
  }
}
