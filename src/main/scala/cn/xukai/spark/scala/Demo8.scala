package cn.xukai.spark.scala

/**
  * Created by kaixu on 2018/1/4.
  */
object Demo8 {
  def main(args: Array[String]): Unit = {
    val str = "109.153.230.107 wangwu"
    val arrs = str.split(" ")
    for(s <- arrs){
      println(s)
    }
  }
}
