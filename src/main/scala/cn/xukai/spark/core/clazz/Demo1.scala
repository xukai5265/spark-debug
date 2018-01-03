package cn.xukai.spark.core.clazz

/**
  * Created by kaixu on 2017/12/27.
  */
class Demo1(x:Int,y:Int) {
  private var i : String = "abc"
  var ii :String = "abc"
}
object Demo1{
  def main(args: Array[String]): Unit = {
    val demo1 = new Demo1(1,2)
    demo1.i = "bcd"
    println(demo1.i)
  }
}
