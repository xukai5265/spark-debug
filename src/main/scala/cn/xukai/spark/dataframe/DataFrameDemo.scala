package cn.xukai.spark.dataframe

import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2017/10/11.
  */
object DataFrameDemo{
  def main(args: Array[String]): Unit = {
    val spark:SparkSession = SparkSession.builder().master("local[2]").appName("csvDataFrame").getOrCreate()
    val student = spark.read.option("header", "true").csv("StudentData.csv")
    student.show(false)
  }
}
