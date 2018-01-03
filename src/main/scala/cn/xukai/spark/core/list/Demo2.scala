package cn.xukai.spark.core.list

/**
  * Created by kaixu on 2017/12/27.
  */
object Demo2 {
  def main(args: Array[String]): Unit = {
    // 模式匹配
    val shuffledData = List(6, 3, 5, 6, 2, 9, 1)
    println(sortList(shuffledData))

    // 排序
    def sortList(dataSet: List[Int]): List[Int] = dataSet match {
      case head :: tail => compute(head, sortList(tail))
      case List()       => List()
    }

    def compute(data: Int, dataSet: List[Int]): List[Int] = dataSet match {
      case List() => List(data);
      // 如果集合第一个元素值小于data值，data放在第一个位置
      case head :: tail => if (data <= head) data :: dataSet
      // 如果不小于data值，进行下次比较
      else head :: compute(data, tail);
    }
  }
}
