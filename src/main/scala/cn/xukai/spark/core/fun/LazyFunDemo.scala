package cn.xukai.spark.core.fun

import scala.io.Source

/**
  * Created by kaixu on 2017/12/27.
  */
object LazyFunDemo {
  def readFile() :Unit = {
    // 赋值时不会检查文件是否存在，而是等到真正运行时，才会加载。
    lazy val file =Source.fromFile("E:\\Scala.txt");
    println("==========scala=========");
    for(line <- file.getLines()){
      println(line);
    }
  }

  def main(args: Array[String]): Unit = {
    readFile();
  }
}
