package cn.xukai.spark.core.array

/**
  * Created by kaixu on 2017/12/27.
  * 静态数组
  */
object ArrayDemo {
  def main(args: Array[String]): Unit = {
    // 定义字符串数组
    val stringArray = new Array[String](10)
    // 给数组赋值
    stringArray(1)="a"
    // 遍历数组
    for(i <- stringArray){
      println(i)
    }
  }
}
