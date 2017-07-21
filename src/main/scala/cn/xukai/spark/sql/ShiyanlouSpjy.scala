package cn.xukai.spark.sql

import java.net.URI
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2017/7/21.
  * 日交易额计算
  *
  * 数据格式如下：
      2017-5-01,1555.05,10001
      2017-5-01,2423.15,10042
      2017-5-01,215.20
      2017-5-01,2356.05,10043
      2017-5-01,2748.87,10044
      2017-5-01,2613.02,10045
      2017-5-02,2515.05,10001
      2017-5-02,2323.35,10022
      2017-5-01,445.30
      2017-5-01,2326.05,10033
      2017-5-01,2444.20,10434
      2017-5-01,2323.24,10054
      2017-5-02,2456.23,10441
      2017-5-03,2434.42,10434
      2017-5-02,2436.34,10032
      2017-5-03,2738.23,10432
      2017-5-03,2323.34,10233
      2017-5-03,2555.05,10001
      2017-5-03,2677.15,10434
      2017-5-03,345.52
      2017-5-03,2345.05,10553
      2017-5-02,2458.54,10455
      2017-5-03,2454.45,10344
      2017-5-03,1255.12,10443
      2017-5-04,2323.32,10432
      2017-5-04,2356.22,10531
      2017-5-02,3248.22,10533
      2017-5-05,2613.02,10233


  需求： 计算每日所有商品的交易总额

  maven 打包后
  在spark 客户端执行
  spark-submit --class cn.xukai.spark.sql.ShiyanlouSpjy 包名
  */
object ShiyanlouSpjy {

  case class Model(date: String, sale_amount: Double, sale_id: String)

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .master("spark://192.168.107.128:7077")
      .appName("日交易额计算")
      .getOrCreate()
    import spark.implicits._
    val peopleDF = spark.sparkContext.textFile("hdfs://192.168.107.128:9000/data/shiyanlou/data.txt")
      .map(_.split(","))
      .filter(_.size == 3)
      .map(attributes => Model(attributes(0), attributes(1).trim.toDouble, attributes(2)))
      .toDF
    peopleDF.show()
    peopleDF.createOrReplaceTempView("t_spjy")
    import spark.sql
    val res = sql("select date,sum(sale_amount) res from t_spjy group by date order by date desc")
    /**
      * 将结果写入到hdfs中
      */

    //判断hfds中是否存在该目录
    val path = "hdfs://192.168.107.128:9000/data/shiyanlou/data"
    val outFile = new Path(path)
    val hdfs = FileSystem.get(new URI("hdfs://192.168.107.128:9000"),new Configuration())
    if(hdfs.exists(outFile)){
      hdfs.delete(outFile,true)
    }
    res.write.format("csv").save(path)
    spark.close()
  }
}
