package cn.xukai.spark.core.list

/**
  * Created by kaixu on 2017/12/27.
  */
object Demo {
  def main(args: Array[String]): Unit = {
    val a = 1 :: 2 :: 3 :: 4 :: Nil
    println(a)
    // list与list之间进行连接符:::
    println(List(1,2,3,4) ::: List(4,5,6,7,8) ::: List (10,11));
//    println(List(1,2,3,4) :::(List(4,5,6,7,8):::List(10,11)));
//    println(List(1,2,3,4) :::List(4,5,6,7,8):::List(10,11));
//
//    // lenght方法特别慢
    println(List(1,2,3,4).length)

    var list = List[Int](1)
    list :+= 4
    list +:= 3
    list.foreach(println)
  }
}
