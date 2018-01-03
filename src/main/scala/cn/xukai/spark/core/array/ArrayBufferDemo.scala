package cn.xukai.spark.core.array

import scala.collection.mutable.ArrayBuffer

/**
  * Created by kaixu on 2017/12/27.
  * 动态数组
  */
object ArrayBufferDemo {
  def main(args: Array[String]): Unit = {
    // 定义一个动态数组
    val arrBuff = new ArrayBuffer[Int]()
    // 在动态数组末尾添加一个元素
    arrBuff+=1
    dispaly(arrBuff)
    // 在动态数组末尾添加多个元素
    arrBuff+=(2,3,4,5)
    dispaly(arrBuff)
    // 在动态数组末尾追加一个集合
    arrBuff ++= Array(6,7,8)
    dispaly(arrBuff)
    // 添加元素
    arrBuff.insert(2,6)
    arrBuff.insert(2,7,8,9)
    // 删除元素
    arrBuff.remove(2)
    // 从第3个元素开始移除3个元素
    arrBuff.remove(3,3)
    // 将动态数组转化成数组
    arrBuff.toArray

    println(arrBuff.mkString("and"))
  }

  def dispaly(buff : ArrayBuffer[Int]): Unit ={
    buff.foreach(println)
  }

}
