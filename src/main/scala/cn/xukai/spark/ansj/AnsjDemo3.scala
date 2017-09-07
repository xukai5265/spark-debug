package cn.xukai.spark.ansj
import org.ansj.recognition.impl.StopRecognition
import org.ansj.splitWord.analysis.{BaseAnalysis}

import scala.io.Source

/**
  * Created by kaixu on 2017/8/28.
  */
object AnsjDemo3 extends App{
  val str = "金融白领被骗入传销 每天面壁睡三四小时  扬子晚报讯(通讯员 王国禹 记者 万凌云)26岁的广东籍男子小鄢，原是深圳的一名金融白领，但没想到被“准女友”骗到丹阳，误入传销窝点。数天后，其父母未见儿子信息，便千里迢迢到丹阳向警方求助。接到报警后，丹阳警方经多方努力，成功将小鄢解救，并将其送上返乡的列车。28日，警方介绍，小鄢在传销窝点期间，手机被没收，每天被要求坐在板凳上面壁反省，每天只能睡三四个小时，而一旦外出，年纪轻轻的他不仅会有多人“保护”，还会被左右“搀扶”。  在警方的帮助下，被成功解救的小鄢，目前已和家人一起返乡，而警方对相关传销分子的调查，还在进行中。";
  val stopRecognition1 = new StopRecognition()
  //读入停词
  val stopFile = Source.fromFile("stopword.txt","utf-8")
  for(stopfile <- stopFile.getLines()){
    stopRecognition1.insertStopWords(stopfile)
  }
  stopFile.close()
  //去除标点符号
  stopRecognition1.insertStopNatures("w")

  val res = BaseAnalysis.parse(str).recognition(stopRecognition1).toStringWithOutNature(" ")
  println(res)
}
