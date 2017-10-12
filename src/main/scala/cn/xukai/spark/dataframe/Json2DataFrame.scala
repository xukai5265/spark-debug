package cn.xukai.spark.dataframe

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * Created by kaixu on 2017/10/12.
  */
object Json2DataFrame {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[2]")
    conf.set("spark.sql.warehouse.dir","file:/D:/spark/warehouse")
    conf.set("spark.sql.shuffle.partitions","20")
    val sparkSession = SparkSession.builder().appName("RDD to DataFrame")
      .config(conf).getOrCreate()
    //通过代码的方式,设置Spark log4j的级别
    sparkSession.sparkContext.setLogLevel("WARN")
    import sparkSession.implicits._
    val jsonDF = json2DataFrame(sparkSession)
    jsonDF.printSchema()
    jsonDF.show()
    // 将查询出的字段保存到parquet 文件
    jsonDF.select($"name").write.save("file:/D:/spark/warehouse/demo1.parquet")
    // 由parquet文件 转为DataFrame 对象
    val parquetDF = parquet2DataFrame(sparkSession)
    parquetDF.show()
    // 将结果保存到csv 文件
    parquetDF.write.option("header","true").csv("file:/D:/spark/warehouse/demo3.csv")
  }

  /**
    * json => DataFrame
    * @param sparkSession
    * @return
    */
  def json2DataFrame(sparkSession: SparkSession):DataFrame={
    sparkSession.read.json("file:/D:/spark/warehouse/people.json")
  }

  /**
    * parquet => DataFrame
    * @param sparkSession
    * @return
    */
  def parquet2DataFrame(sparkSession: SparkSession):DataFrame={
    sparkSession.read.parquet("file:/D:/spark/warehouse/demo1.parquet")
  }
}
