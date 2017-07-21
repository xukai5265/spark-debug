package cn.xukai.spark.scala

/**
  * Created by kaixu on 2017/7/20.
  */
class ClassStudy {
  private var mimima = 30
  def add(c:Int):Int ={
    ClassStudy.sum + c // 访问伴生对象的私有变量sum
  }
}
object ClassStudy {
  private  var sum = 10
  def ad(b:Int)  {sum = sum+ b}
  def main(args: Array[String]): Unit = {
    ad(10)
    println(sum)

    val cs = new ClassStudy // new 只能实例化类，所以这里实例化的是ClassStudy类
    println(cs.mimima) // 访问伴生类的私有变量 mimima

    val result = cs.add(100)
    println(result)
  }
}