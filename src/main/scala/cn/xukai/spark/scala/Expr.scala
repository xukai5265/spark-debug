package cn.xukai.spark.scala

/**
  * Created by kaixu on 2017/7/21.
  */
abstract class Expr {

  /**
    * 变量
    * @param name
    */
  case class Var(name:String) extends Expr

  /**
    * 数字
    * @param num
    */
  case class Number(num:Double) extends Expr

  /**
    * 单操作符
    * @param operator
    * @param arg
    */
  case class UnOp(operator:String, arg:Expr) extends Expr

  /**
    * 和双操作符
    * @param operator
    * @param left
    * @param right
    */
  case class BinOp(operator:String,left:Expr,right:Expr) extends Expr
}
