package cn.xukai.spark.ansj

import java.io.{File, PrintWriter}
import java.nio.charset.CodingErrorAction

import cn.xukai.spark.ansj.AnsjDemo1.file
import org.ansj.recognition.impl.StopRecognition
import org.ansj.splitWord.analysis.BaseAnalysis

import scala.io.{Codec, Source}

/**
  * Created by kaixu on 2017/8/28.
  */
object AnsjDemo2 extends App{
  // https://gxnotes.com/article/94456.html   解决乱码问题
  implicit val codec = Codec("GBK")
  codec.onMalformedInput(CodingErrorAction.REPLACE)
  codec.onUnmappableCharacter(CodingErrorAction.REPLACE)

  val stopRecognition1 = new StopRecognition()
  //读入停词
  val stopFile = Source.fromFile("stopword.txt","utf-8")
  for(stopfile <- stopFile.getLines()){
    stopRecognition1.insertStopWords(stopfile)
  }
  stopFile.close()
  //去除标点符号
  stopRecognition1.insertStopNatures("w")


  // 读入文件
  val file = new File("D:\\资料\\语料\\tc-corpus-answer\\answer")
  val outFile = new PrintWriter("D:\\资料\\语料\\tc-corpus-answer\\result\\C3\\c3.txt")
  for(d <- subDir(file)){
    val fileName = d.getName
    val indexs = fileName.split("-")
    println("文件名："+indexs(0))
    val file = Source.fromFile(d)
    val buf = new StringBuilder;
    for(line <- file.getLines){
      buf ++= line
    }
    val res = BaseAnalysis.parse(buf.toString()).recognition(stopRecognition1).toStringWithOutNature(" ")
    // 写出结果
    outFile.write(indexs(0)+","+res)
    outFile.write("\n")
    outFile.flush()
    println("写入成功！")
  }
  outFile.close()




  def subDir(dir:File):Iterator[File] ={
    val dirs = dir.listFiles().filter(_.isDirectory())
    val files = dir.listFiles().filter(_.isFile())
    files.toIterator ++ dirs.toIterator.flatMap(subDir _)
  }
}

