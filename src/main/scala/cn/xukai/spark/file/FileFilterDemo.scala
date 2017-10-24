package cn.xukai.spark.file

import java.io.{File, FileFilter, FilenameFilter}

import org.apache.spark.mllib.classification.LogisticRegressionModel

/**
  * Created by kaixu on 2017/10/18.
  */
object FileFilterDemo {
  def main(args: Array[String]): Unit = {
    val file = new File("D:\\spark\\warehouse\\demo1.parquet")
//    file.listFiles(new FileFilter(){
//      override def accept(pathname: File): Boolean = {
//        val s: String = pathname.getName.toLowerCase
//        if (s.endsWith(".parquet")) {
//          println(s)
//        }
//        return false
//      }
//    })

    file.listFiles(new FilenameFilter {
      override def accept(dir: File, name: String): Boolean = {
        if(name.endsWith(".parquet"))
          println(name)
        return false
      }
    })
  }
}
