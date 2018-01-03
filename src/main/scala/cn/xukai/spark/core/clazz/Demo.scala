package cn.xukai.spark.core.clazz

/**
  * Created by kaixu on 2017/12/27.
  */
class Demo {
  var str: String = "abc";
  def this(str:String){
    this()
    this.str = str
  }
}
object Demo{
  def main(args: Array[String]): Unit = {
    val demo = new Demo("b")
    println(demo.str)
  }
}
