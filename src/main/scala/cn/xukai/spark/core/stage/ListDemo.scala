package cn.xukai.spark.core.stage

/**
  * Created by kaixu on 2017/12/25.
  */
object ListDemo {
  def main(args: Array[String]): Unit = {
    var list = List[String] {"abc"}
    println(list)
    // 在列表头部添加一个元素
    list = "head" +: list
//    list = list. +: ("head")
    // 在列表尾部添加一个元素
//    list = list :+ "weiba"
    list = list. :+("weiba")
    println(list)



    for(i <- 0 to 10){
      // 新元素从List 的头部添加
//      list = list. +: (""+i)
      // 新元素从List 的尾部添加
      list = list. :+ (""+i)
      println(list)
    }

    for(i <- 0 to 10){
      list = list.drop(i)
      println(i)
      println(list)
    }
  }
}
