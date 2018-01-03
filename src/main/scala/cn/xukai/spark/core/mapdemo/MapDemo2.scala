package cn.xukai.spark.core.mapdemo

import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2018/1/3.
  */
object MapDemo2 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[*]").appName("count").getOrCreate()
    val sc = spark.sparkContext
    val nums = sc.parallelize(0 to 10,5)
    val rdd1 = nums.mapPartitionsWithIndex{
      (x,iter)=>{
        var list = List[String]()
        while (iter.hasNext){
          list :+= (x+" : "+iter.next())
        }
        list.iterator
      }
    }
    val rdd2 = nums.mapPartitionsWithIndex{
      (x,iter)=>{
        val result = scala.collection.mutable.Map[Int,List[Int]]()
        var list = List[Int]()
        while (iter.hasNext){
          list :+= iter.next()
        }
        result.put(x,list)
        result.iterator
      }
    }
    // 错误写法
    val rdd3 = nums.mapPartitionsWithIndex{
      (x,iter)=>{
        val result = scala.collection.mutable.Map[Int,List[Int]]()
        var list = List[Int]()
        while (iter.hasNext){
          list :+= iter.next()
        }
        // 错误位置
        result.put(x,list).iterator
      }
    }
    println("rdd1...")
    rdd1.collect().foreach(println)
    println("rdd2...")
    rdd2.collect().foreach(println)
    println("rdd3...")
    rdd3.collect().foreach(println)
  }
}
