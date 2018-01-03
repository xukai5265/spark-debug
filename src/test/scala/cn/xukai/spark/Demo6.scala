package cn.xukai.spark

import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2018/1/3.
  */
object Demo6 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[*]").appName("count").getOrCreate()
    val sc = spark.sparkContext
    val nums = sc.parallelize(0 to 10,5)
    val rdd2 = nums.mapPartitionsWithIndex{
      (x,iter)=>{
        var result = List[String]()
        while (iter.hasNext){
          result.::(x+"|"+iter.next())
        }
        result.iterator
      }
    }
    rdd2.collect().foreach(println)
  }
}
