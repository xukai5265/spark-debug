package cn.xukai.spark.scala

/**
  * Created by kaixu on 2017/8/4.
  */
object Demo7 extends App{
//  print(Email("xukai", "126.com"))
  println(Email.apply("xukai", "126.com"))
  println(Email.unapply("xukai5265@126.com"))
}
