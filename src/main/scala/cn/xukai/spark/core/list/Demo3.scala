package cn.xukai.spark.core.list

import scala.collection.mutable.ArrayBuffer

/**
  * Created by kaixu on 2017/12/27.
  */
object Demo3 {
  def main(args: Array[String]): Unit = {



    val a = List(1,2,3)
    val b = a.head :: a.tail

    val map = Map("a"->1,"b"->2)
    for((k,v)<- map){
      println(k)
    }
    val f = (1,"a")
    println(f._1)

    val x = 1
    val y = 1
    val z = x
    val yy = 2
    println(x == y)
    println(z == x)
    println(x == yy)
    val x_1 = new ArrayBuffer[Int]()
    x_1+=1
    val x_2 = new ArrayBuffer[Int]()
    println(x_1 == x_2)
  }
}
