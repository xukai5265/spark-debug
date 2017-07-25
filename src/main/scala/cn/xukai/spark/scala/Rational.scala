package cn.xukai.spark.scala

/**
  * 有理数函数
  * 整数转换为分数的计算 =》 结果使用分数表示
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
  private val g =gcd (n.abs,d.abs)
  var number = n/g
  var denom = d/g
  /**
    * 辅助构造器
    * 理解： 当我们提供的数为整数，就没有必要指定分母。
    * @param n
    * @return
    */
  def this(n:Int)=this(n,1)


  override def toString: String = n + "/" + d

  def +(that:Rational) = new Rational(number * that.denom + that.number* denom,denom * that.denom)

  def -(that:Rational) = new Rational(number * that.denom - that.number* denom,denom * that.denom)

  def *(that:Rational) = new Rational( number * that.number, denom * that.denom)

  def /(that:Rational) = new Rational( number * that.denom, denom * that.number)

  private def gcd(a:Int,b:Int):Int = if(b==0) a else gcd(b, a % b)
}
