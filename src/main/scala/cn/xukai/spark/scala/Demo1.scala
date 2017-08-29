package cn.xukai.spark.scala

/**
  * Created by kaixu on 2017/7/20.
  */
class Demo1 {
  /*
    类的方法分两种，一种是有返回值的，一种是不含返回值的，
    没有返回值的主要是利用代码的“副作用”，比如修改类的成员变量的值或者读写文件等。
    Scala 内部其实将这种函数的返回值定为 Unit（类同 Java 的 void 类型），
    对于这种类型的方法，可以省略掉 = 号，因此如果你希望函数返回某个值，但忘了方法定义中的 =，
    Scala 会忽略方法的返回值，而返回 Unit 。
   */

  def getMax(a:Int,b:Int): Int ={
    if(a>b)
      a
    else
      b
  }
  def main(args: Array[String]): Unit = {

  }

}
