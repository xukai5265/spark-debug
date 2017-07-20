package cn.xukai.spark.scala

/**
  * Created by kaixu on 2017/7/20.
  */
class Rational(n:Int,d:Int) {
  /*
     Scala 中定义为传入构造函数和方法的参数的限制范围，也就是调用这些函数或方法的调用者需要满足
     的条件。
     Scala 中解决这个问题的一个方法是使用 require 方法
     （require 方法为 Predef 对象的定义的一个方法，Scala 环境自动载入这个类的定义，
     因此无需使用 import 引入这个对象)
   */
  require(d!=0)
  println("Created "+n +"/" + d)
  var number = n
  var denom = d
  override def toString: String = n + "/" + d

  def +(that:Rational) =
  new Rational(
    number * that.denom + that.number* denom,denom* that.denom
  )

  def -(that:Rational) = new Rational(number*that.denom-that.number*denom,denom * that.denom)

  def *(that:Rational) = new Rational(number*that.denom*that.number*denom,denom * that.denom)

  def /(that:Rational) = new Rational(number*that.number*that.denom * denom,denom * that.number)
}
