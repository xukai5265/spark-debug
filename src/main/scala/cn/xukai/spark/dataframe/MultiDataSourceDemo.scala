package cn.xukai.spark.dataframe

import java.util.Properties

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2018/6/4.
  * 目的：测试多数据源计算的场景
  */
object MultiDataSourceDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]")
    val sparkSession = SparkSession.builder().appName("test more data source")
      .config(conf).getOrCreate()
    //通过代码的方式,设置Spark log4j的级别
    sparkSession.sparkContext.setLogLevel("WARN")

    // 隐式转换
    import sparkSession.implicits._

    val prop = new Properties()
    prop.setProperty("user","root")
    prop.setProperty("password","123456")

    val data1 = sparkSession.read.textFile("D:\\test_data\\spark\\a.txt")
    val data2 = sparkSession.read.textFile("D:\\test_data\\spark\\b.txt")
    val data3 = sparkSession.read.jdbc("jdbc:mysql://localhost:3306/test","a",prop)

//    prop.setProperty("","")

    val d1 = data1.map(line => (line.split(","),1))
    val d2 = data2.map(line => (line.split(","),1))

    d1.show()
    d2.show()
    data3.show()


    sparkSession.close()

  }
}
