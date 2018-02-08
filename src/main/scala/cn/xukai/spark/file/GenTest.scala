package cn.xukai.spark.file

import java.io.PrintWriter
import java.util.concurrent.Executors

/**
  * Created by kaixu on 2018/1/3.
  */
object GenTest {
  def main(args: Array[String]): Unit = {
    val pools = Executors.newFixedThreadPool(50)
    // 写入文件
    val writerA = new PrintWriter("D:\\test_data\\scala\\A.txt");
    val writerB = new PrintWriter("D:\\test_data\\scala\\B.txt");
    try{
      for(i <- 1 to 50){
        pools.execute(new GenerateData(i,writerA,writerB))
      }
    }finally {
      pools.shutdown()
    }
  }
}
