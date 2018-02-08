package cn.xukai.spark.streaming.attention2commodity

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by kaixu on 2018/2/5.
  */
object StreamingGoods {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    // 当程序第一次启动的时候，他会创建一个新的StreamingContext，设置所有的流，然后调用start()
    // 当程序失败重启时候，他将从检查点目录中，检查点数据重新创建StreamingContext
    // getOrCreate 确保驱动程序在失败的时候自动重启。
    // 缺点： 可能导致rdd 检查点的批次处理时间变长。
    val context = StreamingContext.getOrCreate("checkpoint",functionToCreateContext _)
    val ds = context.socketTextStream("localhost",9999)
    val splitMess  = ds.map{ line =>
      val lineSplit = line.split("::")
      (lineSplit(0),(lineSplit(1).toDouble *.8 + lineSplit(2).toDouble*.6 + lineSplit(3).toDouble*1 + lineSplit(4).toDouble*1)  )
    }
    //将以前的数据和最新一分钟的数据进行求和
    val addFunction = (currValues : Seq[Double],preVauleState : Option[Double]) => {
      val currentSum = currValues.sum
      val previousSum = preVauleState.getOrElse(0.0)
      Some(currentSum + previousSum)
    }

    val totalPayment = splitMess.updateStateByKey(addFunction)
    totalPayment.print()
    context.start()
    context.awaitTermination()
  }
  def functionToCreateContext(): StreamingContext = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("StreamingGoods")
    val ssc = new StreamingContext(sparkConf, Seconds(5))
    ssc.checkpoint("checkpoint")
    ssc
  }
}
