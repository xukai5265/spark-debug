package cn.xukai.spark.scala

/**
  * Created by kaixu on 2017/7/20.
  */
class Demo {
  private var sum=0

  /**
    * scala 方法的参数都是val 类型的。
    * 所以不能再scala 的函数体内不可以修改函数值
    * @param b
    */
  def add(b:Byte):Unit={
//    b=1
    sum+=b
  }





}
