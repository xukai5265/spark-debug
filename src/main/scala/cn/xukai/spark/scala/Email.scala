package cn.xukai.spark.scala

/**
  * Created by kaixu on 2017/8/4.
  */
object Email {
  def apply(name:String,area:String) = print(name+"@"+area)

  def unapply(str:String): Option[(String,String)] = {
    val parts = str.split("@")
    if(parts.length==2) Some(parts(0),parts(1)) else None
  }
}
