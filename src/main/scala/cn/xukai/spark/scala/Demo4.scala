package cn.xukai.spark.scala

import cn.xukai.spark.java.Demo

/**
  * Created by kaixu on 2017/8/2.
  * scala 调用java 代码，返回null值的处理
  */
object Demo4 extends App{
  val res = Demo.getElements("aaaa")
  println(res)
  if(res==null){
    println("is null")
  }


  var floatVar = 3.14
  var intVar = 12
  var stringVar = "和理想挥手"
  printf("浮点型变量为：%f,整形变量为：%d,字符串为：%s",floatVar,intVar,stringVar)
  println()
  // 闭包
  val fs = new Array[()=>Int](4)
  var i = 0;
  while(i < 4){
    fs(i) = () => i
    i = i + 1
  }
  fs.foreach( f => println(f()))

  val fs1 = new Array[()=>Int](4)
  for(i <- 0 to 3){
    fs1(i) = () =>i
  }
  fs1.foreach( f => println(f()))






}
