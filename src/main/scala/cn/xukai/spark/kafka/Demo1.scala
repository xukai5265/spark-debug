package cn.xukai.spark.kafka

/**
  * Created by kaixu on 2018/5/31.
  */
object Demo1 {
  def main(args: Array[String]): Unit = {
    val demo = new Demo("xk")
    println(demo.a)

    println(demo.add(1, 1))
  }
}
