package cn.xukai.spark.ansj
import java.io.PrintWriter

import scala.io.Source

/**
  * Created by kaixu on 2017/8/25.
  */
object AnsjDemo1 extends App{
  val a:Long = 10
  println(Integer.parseInt(a.toString()))


  val webFile=Source.fromURL("http://spark.apache.org")
  webFile.foreach(print)
  webFile.close()

  val file = new PrintWriter("fiveRateDocument.txt")
  file.write(s"尽管心里或者")
  file.flush()
  file.close()

//  val stopRecognition1 = new StopRecognition()
//  //scala 读取文件
//  val file = Source.fromFile("D:\\stopword.txt","utf-8")
//  for(line <- file.getLines)
//  {
//    //手动添加停用词
//    stopRecognition1.insertStopWords(line)
//  }
//  file.close
//  val str = "对不起说了太多，let go 爱只是假象。却迷失方向！招商银行分期业务，黄昏我站在高高的山岗。";
//  stopRecognition1.insertStopNatures("w") //去除标点符号
//  println(BaseAnalysis.parse(str).recognition(stopRecognition1).toStringWithOutNature(" "))
}
