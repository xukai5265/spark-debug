package cn.xukai.spark.ml
import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.NaiveBayes
import org.apache.spark.ml.feature.{HashingTF, IDF, RegexTokenizer, Tokenizer}
import org.apache.spark.sql.SparkSession
/**
  * Created by kaixu on 2017/8/28.
  * http://lxw1234.com/archives/2016/01/605.htm
  * bloom Filter http://blog.csdn.net/jiaomeng/article/details/1495500
  *
  * NaiveBayes
  */
object TestNaiveBayes extends App{

  case class RawDataRecord(label: Int, text: String)
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  val spark = SparkSession.builder().master("spark://192.168.107.128:7077").appName("TokenizeDemo").getOrCreate()
  val sc = spark.sparkContext
  val sentenceDataFrame =  sc.textFile("file:///application/spark-2.2.0-bin-hadoop2.7/examples/jars/sougou").map {
    x =>
      val data = x.split(",")
      RawDataRecord(data(0).toInt, data(1))
  }
  import spark.implicits._
  val srcRDD = sentenceDataFrame.toDF()
  val splits = srcRDD.randomSplit(Array(0.7, 0.3))
  var trainingDF = splits(0).toDF()
  var testDF = splits(1).toDF()
  println("srcRDD: "+srcRDD.count()+" trainingDF: "+trainingDF.count() + " testDF: "+ testDF.count())
  val tokenizer = new Tokenizer().setInputCol("text").setOutputCol("words")
  val hashingtf = new HashingTF().setInputCol(tokenizer.getOutputCol).setOutputCol("features")
  // 计算每个次的idf值
  val idf = new IDF().setInputCol("features").setOutputCol("idf-features")
  val bayesModel = new NaiveBayes()
  //设置机器学习数据流水线
  val pipeline = new Pipeline()
    .setStages(Array(tokenizer, hashingtf, idf, bayesModel))
  val lrModel = pipeline.fit(trainingDF)
  println(lrModel.toString())
  // 预测
  println("预测...")
  val test = spark.createDataset(Seq("世界 何种 优秀 产品 条 亘古 变 真理 木 秀 林 风 摧 天 车 市 例子 3 月末 刚刚 上市 广 汽 本田 冠 道 240 turbo 上市 外界 动力 质疑 马 拉 车 声音 真 事实 组 数据 反驳 上市 短短 三 月 冠 道 240 turbo 冠 道 全 系 达成 订单 5 辆 壮举 7 月 冠 道 全 系 销量 7500 辆 难 想象 款 刚刚 上市 中 大型 豪华 suv 成绩 佳绩 质疑 动力 性能 消费者 蒙蔽 理智 可言 答案 自然 否定 市场 认可 足以 证明 冠 道 240 turbo 优秀",
    "金融 白领 骗 入 传销   面 壁 睡 三 四 小时 扬子 晚报 讯 ( 通讯员   王国 禹   记者   凌 云 ) 26 岁 广东 籍 男子 鄢 原 深圳 名 金融 白领 想到 准 女 友 骗 丹阳 误 入 传销 窝 点 数 天 父母 未 儿子 信息 千里迢迢 丹阳 警方 求助 接到 报警 丹阳 警方 多方 努力 成功 鄢 解救 送 返乡 列车 28 日 警方 介绍 鄢 传销 窝 点 期间 手机 没收 坐 板凳 壁 反省 睡 三 四 小时 外出 年纪 轻轻 保护 搀扶 警方 成功 解救 鄢 家 返乡 警方 相关 传销 分子 调查 中"
  )).toDF("text").select("text")

  val predictions = lrModel.transform(test)
  predictions.show(2)
  //持久化 dataset
//  predictions.write.format("json").save("file:///application/spark-2.2.0-bin-hadoop2.7/examples/jars/sougoures1")

}
