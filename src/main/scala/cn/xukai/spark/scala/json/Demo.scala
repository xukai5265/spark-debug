package cn.xukai.spark.scala.json

import com.alibaba.fastjson.JSON


/**
  * Created by kaixu on 2017/8/3.
  */
object Demo extends App{
  val list = Map("abc"->1,"bcd"->2)
  println(JSON.toJSON(list))
}
