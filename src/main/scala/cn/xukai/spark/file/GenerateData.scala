package cn.xukai.spark.file

import java.io.PrintWriter

/**
  * Created by kaixu on 2018/1/3.
  */
class GenerateData(threadNum:Int,writerA:PrintWriter,writerB:PrintWriter) extends Runnable{

  /**
    * 生成ip
    * @return
    */
  def genIp():String={
    var res = ""
    for(i <- 0 to 3){
      res += (255 * Math.random()).toInt +"."
    }
    res.substring(0,res.lastIndexOf("."))
  }

  /**
    * 获取用户名
    * @return
    */
  def genNames :String ={
    val data = Array("zhangsan","lisi","wangwu","zhaoliu","heiqi","xiaoba","xiaojiu","xiaoshi")
    data((data.size * Math.random()).toInt)
  }

  override def run(): Unit = {
    for(i <- 0.to(200000)){
      val ip = genIp
      val name = genNames
      val res =  ip + " "+ name
      println("thread "+threadNum+"    A:"+i + "    ip:"+ip +"    name:"+name)
      writerA.println(res)
      val ip_b = genIp
      val name_b = genNames
      val res_b =  ip_b + " "+ name_b
      println("thread "+threadNum+"    B:"+i + "    ip:"+ip_b +"    name:"+name_b)
      writerB.println(res_b)
    }
    writerA.close()
    writerB.close()
  }
}
