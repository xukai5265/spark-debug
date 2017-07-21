package cn.xukai.spark.sql

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

/**
  * Created by kaixu on 2017/7/18.
  * spark 读取hive 数据
  * spark 写入数据到hive 表
  *
  *  spark-submit --class cn.xukai.spark.sql.Spark2hive --master local[*] spark.util-1.0-SNAPSHOT.jar
  */
object Spark2hive {
  val warehouseLocation = "spark-warehouse"

  def main(args: Array[String]): Unit = {
    new Array[String](3)
    val spark = SparkSession
      .builder()
      .appName("spark hive simple")
      .config("spark.sql.warehouse.dir", warehouseLocation)
      .enableHiveSupport()
      .getOrCreate()
    import spark.implicits._
    import spark.sql
    //从hive 表中查询数据
    sql("SELECT * FROM sparktest.student").show()


    //写数据到hive 表
    val studentRDD = spark.sparkContext.parallelize(Array("3 Rongcheng M 26","4 Guanhua M 27")).map(_.split(" "))
    val schema = StructType(List(StructField("id", IntegerType, true),StructField("name", StringType, true),StructField("gender", StringType, true),StructField("age", IntegerType, true)))
    val rowRDD = studentRDD.map(p => Row(p(0).toInt, p(1).trim, p(2).trim, p(3).toInt))
    val studentDF = spark.createDataFrame(rowRDD, schema)
    studentDF.show()
    studentDF.createOrReplaceTempView("tempTable")
    sql("insert into sparktest.student select * from tempTable")
    spark.close()
  }
}
