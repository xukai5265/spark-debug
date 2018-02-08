package cn.xukai.spark.ml.sgd

import scala.collection.mutable.HashMap
/**
  * 随机梯度下降
  * Created by kaixu on 2018/1/30.
  */
object SGD{
//  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
//  Logger.getLogger("org.apache.eclipse.jetty.server").setLevel(Level.OFF)
//  val spark = SparkSession.builder().master("local[*]").appName("SGD").getOrCreate()
//  val sc = spark.sparkContext
//  sc.setLogLevel("OFF")

  //创建存储数据集HashMap集合
  val data = new HashMap[Int, Int]()
  //生成数据集内容
  def getData(): HashMap[Int, Int] = {
    for(i <- 1 to 50) {
      data += (i -> (2 * i))  //写入公式y=2x
    }
    data
  }

  //假设a=0
  var a: Double = 0
  //设置步进系数
  var b: Double = 0.1

  //设置迭代公式
  def sgd(x: Double, y: Double) = {
    a = a - b * ((a * x) - y)
  }

  def main(args: Array[String]): Unit = {
    //获取数据集
    val dataSource = getData()
    println("data: ")
    dataSource.foreach(each => println(each + " "))
    println("\nresult: ")
    var num = 1
    //开始迭代
    dataSource.foreach(myMap => {
      println(num + ":" + a + "("+myMap._1+","+myMap._2+")")
      sgd(myMap._1, myMap._2)
      num = num + 1
    })
    //显示结果
    println("最终结果a为 " + a)
  }


}
