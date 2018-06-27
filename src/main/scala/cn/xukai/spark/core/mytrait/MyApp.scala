package cn.xukai.spark.core.mytrait

/**
  * Created by kaixu on 2018/6/6.
  * 实现App 特质
  */
trait MyApp {
  println("I`m my app")
  def main(args:Array[String]): Unit ={
    args.foreach(x => println(s"${System.currentTimeMillis()},$x"))
  }
}
