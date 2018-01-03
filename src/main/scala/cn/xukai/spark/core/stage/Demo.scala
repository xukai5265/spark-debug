package cn.xukai.spark.core.stage

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2017/12/25.
  */
object Demo {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache.spark").setLevel(Level.DEBUG)
    val sparkSession = SparkSession.builder().appName("stage demo").master("local[*]").getOrCreate()
    println(sparkSession.hashCode())
    val sc = sparkSession.sparkContext
    val data = sc.textFile("D:\\test_data\\spark\\wordcount")
    val t_1 = data.flatMap(_.split(" "))
    val t_2 = t_1.map((_, 1))
    val t_3 = t_2.reduceByKey(_ + _)
    println(t_3.toDebugString)
    val t_4 = t_3.collect()
    t_4.foreach(println)

    data.cache()
    val result:Array[(String, Int)] = t_4
    for(res <- result){
      println(res._1+"  "+ res._2)
    }

    // 获取分区数量
    println(data.partitions.size)

    while (true){
      Thread.sleep(10000)
      println(System.currentTimeMillis())
    }
    // 统计rdd 分区中有哪些元素
    //    data.mapPartitionsWithIndex{
    //      (partIds,iter)=>{
    //        var part_map = scala.collection.mutable.Map[String,List[String]]()
    //        while (iter.hasNext){
    //          val part_name = "part_" + partIds
    //          var elem = iter.next()
    //          if(part_map.contains(part_name)) {
    //            var elems = part_map(part_name)
    //            elems ::= elem
    //            part_map(part_name) = elems
    //          }else{
    //            part_map(part_name) = List[String]{elem}
    //          }
    //        }
    //        part_map.iterator
    //      }
    //    }.collect.foreach(println)

    sc.stop()
  }
}
