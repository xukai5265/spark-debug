package cn.xukai.spark.jdbc

import java.sql
import java.sql.DriverManager

import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2018/6/12.
  */
object Rdd2JDBC extends App{
  val spark = SparkSession.builder().master("local[*]").appName("rdd foreach").getOrCreate()
  val sc = spark.sparkContext
  val rdd = sc.parallelize(Seq("abc","bcd","def","efg","hig","jkl","mno"),2)
  rdd.foreach { x =>
    Class.forName("com.mysql.jdbc.Driver")
    val conn:sql.Connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "123456")
    val stmt:sql.Statement = conn.createStatement()
    stmt.execute(s"insert into rddDemo values('${x}')")
  }
  spark.close()
}
