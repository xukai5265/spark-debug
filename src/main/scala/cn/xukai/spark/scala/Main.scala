package cn.xukai.spark.scala

/**
  * Created by kaixu on 2017/7/20.
  */
object Main {
  def main(args: Array[String]): Unit = {
    val demo1 = new Demo1
    val res = demo1.getMax(1,2)
    println(res)

    val d1 = new Rational(1,5)
    println(d1.toString)

    val d2 = new Rational(3,2)

    println(d1.+(d2).toString)

    val res1 = d1 + d2
    println(res1)

    val res2 = d1 - d2
    println(res2)

    val res3 = d1*d2
    println(res3)

    val res4 = d1/d2
    println(res4)
  }
}
