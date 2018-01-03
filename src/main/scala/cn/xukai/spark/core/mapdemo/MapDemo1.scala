package cn.xukai.spark.core.mapdemo

/**
  * Created by kaixu on 2017/12/27.
  */
object MapDemo1 {
  def main(args: Array[String]): Unit = {
    // 动态map
    val scores = scala.collection.mutable.Map("a"->1,"b"->2)
    scores += ("c"->3)
    scores -= ("a")

  }
}
