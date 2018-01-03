package cn.xukai.spark


/**
  * Created by kaixu on 2018/1/2.
  */
object Demo3 {
  def main(args: Array[String]): Unit = {
    val a = Array(1,2,3)
    val b = Array(2,3,4)
    val c = a
    val d = a
    println(a==b) // false   判断值是否相同
    println(a==c) // true
    println(a.eq(b)) // false
    println(c.eq(d))
  }
}
