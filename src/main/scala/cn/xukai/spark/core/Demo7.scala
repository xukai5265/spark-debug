package cn.xukai.spark.core

/**
  * Created by kaixu on 2018/1/26.
  */
object Demo7 extends App{
  val a = List(1,2,3)
  val b = List(2,3,4,2)

  val res = a.intersect(b)

  println(res)
  println(res.size)

  println(b.groupBy(a => a))


  println(2.toDouble/5)

  println("2".toDouble)
  println(2.toDouble)
}
