package cn.xukai.spark.scala

/**
  * Created by kaixu on 2017/7/20.
  */
object Demo3 {
  private var sum = 0;

  def ad(b:Int) {sum+=b}

  def main(args: Array[String]): Unit = {
    ad(10)
    println(sum)
  }
}
