package cn.xukai.spark.core.mapdemo

/**
  * Created by kaixu on 2017/12/27.
  */
object MapDemo {
  def main(args: Array[String]): Unit = {
    // 定义map
    val map = Map("a"->1,"b"->2)
    // 遍历map
    val res = for((k,v)<-map)yield v
    println(res)
  }
}
