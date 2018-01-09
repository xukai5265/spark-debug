package cn.xukai.spark.ml.logisiticRegression

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.DoubleType

/**
  * Created by kaixu on 2018/1/5.
  */
object LogisiticRegressionTest {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local").appName("LogisiticRegressionTest").getOrCreate()
    spark.sparkContext.setLogLevel("WARN")
    val bank_Marketing_Data = spark.read.option("header",true).option("inferSchema","true").csv("D:\\资料\\语料\\逻辑回归\\bank_marketing_data.csv")
    println("all columns data:")
    bank_Marketing_Data.show(5)
    // 选择营销数据的11 个字段，并将age、previous、duration 三个字段有Integer转为Double类型
    val select_data = bank_Marketing_Data.select("age",
                                                "job",
                                                "marital",
                                                "default",
                                                "housing",
                                                "loan",
                                                "duration",
                                                "previous",
                                                "poutcome",
                                                "empvarrate",
                                                "y")
                                                .withColumn("age",bank_Marketing_Data("age").cast(DoubleType))
                                                .withColumn("duration",bank_Marketing_Data("duration").cast(DoubleType))
                                                .withColumn("previous",bank_Marketing_Data("previous").cast(DoubleType))
    // 显示指定的11个字段
    println("11 columns data:")
    select_data.show(5)

    // 显示营销数据的数据量
    println("data count:"+select_data.count())

    // 对数据进行概要统计
    val summary = select_data.describe()
    println("Summary Statistics:")
    /*
      统计信息输出包括12列：
      第1列为统计值，总数、平均值、方差值、最小值、最大值;
      第2-12列为指定的数据字段值。从中可以看出每一列的总数、平均值、方差值、最小值、最大值是多少。如果某列内容不是数值，则某些统计信息会是null。
     */
    summary.show()

    //查看每一列所包含的不同值数量
    val columnNames = select_data.columns
    val uniqueValues_PerField = columnNames.map{field => field+":"+select_data.select(field).distinct().count()}
    println("unique values for each field :")
    uniqueValues_PerField.foreach(println)
    // 显示概要统计信息
    spark.close()
  }
}
