package cn.xukai.spark.kafka

/**
  * Created by kaixu on 2018/5/31.
  */
class Demo(name:String,age:Int) {
  var n = name
  var a = age
  def this(name:String){
    this(name,20)
  }

  def add(a:Int,b:Int) : Int = a + b
}
