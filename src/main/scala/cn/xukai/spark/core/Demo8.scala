package cn.xukai.spark.core

/**
  * Created by kaixu on 2018/2/27.
  * map & flatMap 的区别
  */
object Demo8 extends App{

  val books = List("Hadoop","Hive","HDFS")
  val A1 = books.map{a =>
    println(s"a= $a")
    a.toUpperCase()
  }
  val B1 = books.flatMap{ b =>
    println(s"b= $b")
    b.toUpperCase()
  }

  val C1 = books.map(c => c.toUpperCase()).seq
  println(s"A1= $A1")
  println(s"B1= $B1")
  println(s"C1= $C1")

}
