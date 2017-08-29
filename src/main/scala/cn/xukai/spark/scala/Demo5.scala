package cn.xukai.spark.scala

import scala.collection.mutable.ArrayBuffer

/**
  * Created by kaixu on 2017/8/2.
  * 变长数组 ArrayBuffer
  * 类似java 的ArrayList
  */
object Demo5 extends App{
  val buffArray = new ArrayBuffer[Int]()
  buffArray+=1
  for (i <- 0 to 10){
    buffArray+=i
  }
  println(buffArray.mkString(","))


  val setVal = Set(1,2,3,4,5,1,2,3,4)
  println(setVal.mkString(","))  // 不重复
  val listVal = List(1,2,3,4,5,1,2,3,4)
  println(listVal.mkString(",")) // 可重复
  // 创建两个不同类型元素的元组
  val x = (10, "Runoob")
  val mapVal = ("x"->1,"y"->2)
  val y:Option[Int] = Some(5)

  val setIter = setVal.iterator
  println(setIter.max)
}
