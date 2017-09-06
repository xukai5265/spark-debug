package cn.xukai.spark.core
import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2017/9/6.
  * 演示 spark在没有 action 函数或者foreach函数时，spark 不会执行
  * 执行过程中并没有发现 打印 running...
  */
object Demo1 extends App{
  val spark = SparkSession.builder().appName("demo").master("spark://192.168.107.128:7077").getOrCreate()
  spark.sparkContext.addJar("D:\\workspace\\sparkutil\\target\\spark.util-1.0-SNAPSHOT.jar")
  val rdd = spark.sparkContext.parallelize(Seq(("a",1),("b",2),("c",3)))
  val flag = 0
  rdd.map{ line =>
    println("running...")
    line._1
  }
  spark.close()
}
