package cn.xukai.spark.ansj

import java.io.PrintWriter

import cn.xukai.spark.ansj.AnsjDemo2.outFile
import cn.xukai.spark.jdbc.BaiduNewsResult
import org.ansj.recognition.impl.StopRecognition
import org.ansj.splitWord.analysis.BaseAnalysis

import scala.io.Source

/**
  * Created by kaixu on 2017/9/7.
  * 将分词的结果写入到文件中
  */
object AnsjUtils {
  val stopRecognition1 = new StopRecognition()
  //读入停词
  val stopFile = Source.fromFile("stopword.txt", "utf-8")
  for (stopfile <- stopFile.getLines()) {
    stopRecognition1.insertStopWords(stopfile)
  }
  stopFile.close()
  //去除标点符号
  stopRecognition1.insertStopNatures("w")

  val outFile = new PrintWriter("trainData.txt")
  def getFC(obj: BaiduNewsResult): Unit = {
    val words = BaseAnalysis.parse(obj.getContent).recognition(stopRecognition1).toStringWithOutNature(" ")
    println(words)
    // 写入文件中
    outFile.write(obj.getLabel+","+words)
    outFile.write("\n")
    outFile.flush()
  }
}
