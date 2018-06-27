package cn.xukai.spark.rdd

import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2018/6/5.
  * 区分 map & flatmap 区别
  */
object Demo {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().master("local[*]").appName("demo").getOrCreate()
    val sc = sparkSession.sparkContext
    sparkSession.sparkContext.setLogLevel("ERROR")
//    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
//    val rdd = sc.parallelize(1 to 5)
//    val fm = rdd.map(x => (1 to x)).collect()
//    val fm = rdd.flatMap(x => (1 to x)).collect()
//    fm.foreach(x => print(x + " "))

    val rdd1 = sc.parallelize(1 to 3)
    val rdd2 = sc.parallelize(3 to 5)
    val unionRdd = rdd1.union(rdd2)
    unionRdd.collect().foreach(x => print(x + " "))

    sparkSession.close()
  }
}
