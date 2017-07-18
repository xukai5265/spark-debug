package cn.xukai.spark.sql

import org.apache.spark.sql.SparkSession

/**
 *
 * spark-sql hello world
  * spark sql : datasets 将rdd 中字段用scheme表示
 */

object App {
  case class Person(name: String, age: Long)
  def main(args: Array[String]): Unit = {
    val ss = SparkSession
              .builder()
              .appName("sparkSession")
              .master("spark://192.168.107.128:7077")
              .getOrCreate()
    ss.sparkContext.addJar("D:\\workspace\\sparkutil\\target\\spark.util-1.0-SNAPSHOT.jar")
    val sqlContext = ss.sqlContext
    // 创建DataFrames
    val dataset = ss.read.json("hdfs://192.168.107.128:9000/data/spark/people.json")
    // 查看数据
    dataset.show()

    //注册临时表
    dataset.createOrReplaceTempView("people")
    val sqlDF = sqlContext.sql("select * from people")
    sqlDF.show()

    //注册全局临时表
    dataset.createOrReplaceGlobalTempView("global_people")
    // 注意： global_temp 是数据库
    ss.newSession().sql("select * from global_temp.global_people").show()


    /*
      spark sql 将rdd 转 datasets 有两种方法：
      1.第一种方法使用反射来推断包含特定类型对象的RDD模式。
      2.第二种方法是通过编程接口，允许您构建一个模式，然后将其应用到现有的RDD。
     */
    //使用反射推理模式 rdd -> dataset
    import ss.implicits._

    val peopleDF = ss.sparkContext
      .textFile("hdfs://192.168.107.128:9000/data/spark/people.txt")
      .map(_.split(","))
      .map(attributes => Person(attributes(0), attributes(1).trim.toInt))
      .toDF()


    peopleDF.createOrReplaceTempView("people")
    val teenagersDF = ss.sql("SELECT name, age FROM people WHERE age BETWEEN 13 AND 19")
    teenagersDF.map(teenager => "Name: " + teenager(0)).show()
    println("is over...")

    ss.close()

  }
}
