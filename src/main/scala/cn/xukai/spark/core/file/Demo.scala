package cn.xukai.spark.core.file

import scala.io.Source

/**
  * Created by kaixu on 2017/12/27.
  */
object Demo {
  def main(args: Array[String]): Unit = {
    var webFile = Source.fromURL("http://kaijiang.500.com/shtml/ssq/17152.shtml","gbk")
    webFile.foreach { print(_)}
    webFile.close()
  }
}
