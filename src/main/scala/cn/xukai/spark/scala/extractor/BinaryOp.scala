package cn.xukai.spark.scala.extractor

import cn.xukai.spark.scala.Rational

/**
  * Created by kaixu on 2017/7/25.
  * 分解出二元表达式的左右操作数。
  * 1 . 需要理解apply 与 unapply 的用法
  * 2 . 需要理解Option 数据模型
  * 3 . 需要理解模式匹配
  * 4 . 理解特质的意义
  */
trait BinaryOp {
  // 定义操作符
  val op :String

  def apply(str1:String,str2:String):String = str1 + op + str2

  /**
    * 分解操作符
    * @param str
    * @return
    */
  def unapply(str:String):Option[(String,String)]={
    val index = str.indexOf(op)
    if(index > 0)
      Some(str substring(0,index),str substring(index+1))
    else None
  }

  /**
    * 计算
    * @param str
    * @return
    */
  def eval(str:String):Rational = str match{
    case Bracket(part1,expr,part2) => eval(part1 +  eval(expr) + part2)
    case Add(expr1,expr2)=> eval(expr1) + eval(expr2)
    case Subtract(expr1,expr2) => eval(expr1) - eval(expr2)
    case Multiply(expr1,expr2) => eval(expr1) * eval(expr2)
    case Divide(expr1,expr2) => eval(expr1) / eval(expr2)
    case _ => new Rational(str.trim toInt,1)
  }


  def main(args: Array[String]): Unit = {
    val str="1+2+(3*5)+3+3*(3+(3+5))"
//    val str ="1+2"
//    println(eval(str))
    println(eval("5*(5-1/5)"))
    /*
          5   1    4    25    100    24
          - - - =  -  * -   = -    = -
          5   5    5    5     5      1
     */
  }
}
object Add extends {val op ="+"} with BinaryOp
object Subtract extends {val op ="-"} with BinaryOp
object Multiply extends {val op ="*"} with BinaryOp
object Divide extends {val op="/"} with BinaryOp
