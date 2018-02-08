package cn.xukai.spark.ml.logisiticRegression

import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator
import org.apache.spark.ml.feature.{OneHotEncoder, StringIndexer, VectorAssembler}
import org.apache.spark.ml.param.ParamMap
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.DoubleType

/**
  * Created by kaixu on 2018/1/5.
  * 运用逻辑回归分析银行营销数据
  *
  * 字段名称	中文含义
      age	    客户年龄
      job	    客户职业
      marital	婚姻状况
      default	是否有信用违约
      housing	是否有住房
      loan	  是否有住房贷款
      duration  最后一次联系持续时间(秒)
      previous	之前活动中与用户联系次数
      poutcome	之前市场营销活动的结果
      empvarrate	就业变化速率
      y	        目标变量，本次活动实施结果：是否同意存款
  */
object LogisiticRegressionTest2 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local").appName("LogisiticRegressionTest2").getOrCreate()
    spark.sparkContext.setLogLevel("OFF")
    val bank_Marketing_Data = spark.read.option("header",true).option("inferSchema","true").csv("D:\\资料\\语料\\逻辑回归\\bank_marketing_data.csv")
    println("all columns data:")
    bank_Marketing_Data.show(5)
    // 选择营销数据的11 个字段，并将age、previous、duration 三个字段有Integer转为Double类型
    val select_data = bank_Marketing_Data.select("age",
      "job",
      "marital",
      "default",
      "housing",
      "loan",
      "duration",
      "previous",
      "poutcome",
      "empvarrate",
      "y")
      .withColumn("age",bank_Marketing_Data("age").cast(DoubleType))
      .withColumn("duration",bank_Marketing_Data("duration").cast(DoubleType))
      .withColumn("previous",bank_Marketing_Data("previous").cast(DoubleType))
    // 显示指定的11个字段
    println("11 columns data:")
    select_data.show(5)
    // 特征工程
    // 对 job 使用 StringIndex 和OneHotEncoder 操作
    val indexer = new StringIndexer().setInputCol("job").setOutputCol("jobIndex")
    val indexed = indexer.fit(select_data).transform(select_data)
    indexed.printSchema()
    indexed.show()

    val encoder = new OneHotEncoder().setDropLast(false).setInputCol("jobIndex").setOutputCol("jobVec")
    val encoded = encoder.transform(indexed)
    encoded.show
    encoded.printSchema()

    //marital
    val maritalIndexer = new StringIndexer().setInputCol("marital").setOutputCol("maritalIndex")
    val maritalIndexed = maritalIndexer.fit(encoded).transform(encoded)
    val maritalEncoder = new OneHotEncoder().setDropLast(false).setInputCol("maritalIndex").setOutputCol("maritalVec")
    val maritalEncoded = maritalEncoder.transform(maritalIndexed)

    //default
    val defaultIndexer = new StringIndexer().setInputCol("default").setOutputCol("defaultIndex")
    val defaultIndexed = defaultIndexer.fit(maritalEncoded).transform(maritalEncoded)
    val defaultEncoder = new OneHotEncoder().setDropLast(false).setInputCol("defaultIndex").setOutputCol("defaultVec")
    val defaultEncoded = defaultEncoder.transform(defaultIndexed)

    //housing
    val housingIndexer = new StringIndexer().setInputCol("housing").setOutputCol("housingIndex")
    val housingIndexed = housingIndexer.fit(defaultEncoded).transform(defaultEncoded)
    val housingEncoder = new OneHotEncoder().setDropLast(false).setInputCol("housingIndex").setOutputCol("housingVec")
    val housingEncoded = housingEncoder.transform(housingIndexed)

    // loan
    val loanIndexer = new StringIndexer().setInputCol("loan").setOutputCol("loanIndex")
    val loanIndexed = loanIndexer.fit(housingEncoded).transform(housingEncoded)
    val loanEncoder = new OneHotEncoder().setDropLast(false).setInputCol("loanIndex").setOutputCol("loanVec")
    val loanEncoded = loanEncoder.transform(loanIndexed)

    // poutcome
    val poutcomeIndexer = new StringIndexer().setInputCol("poutcome").setOutputCol("poutcomeIndex")
    val poutcomeIndexed = poutcomeIndexer.fit(loanEncoded).transform(loanEncoded)
    val poutcomeEncoder = new OneHotEncoder().setDropLast(false).setInputCol("poutcomeIndex").setOutputCol("poutcomeVec")
    val poutcomeEncoded = poutcomeEncoder.transform(poutcomeIndexed)

    poutcomeEncoded.show()
    poutcomeEncoded.select("jobVec","maritalVec", "defaultVec","housingVec","poutcomeVec","loanVec","age","duration","previous","empvarrate").show()
    poutcomeEncoded.printSchema()


    // 建立逻辑回归模型进行预测

    // 实例化一个向量组装器对象
    // 将向量类型字段("jobVec","maritalVec","defaultVec","housingVec","poutcomeVec","loanVec")
    // 和字符型字段("age","duration","previous","empvarrate")
    // 形成一个新字段：features 其中包含所有特征值
    val  vectorAssembler = new VectorAssembler().setInputCols(Array("jobVec","maritalVec", "defaultVec","housingVec","poutcomeVec","loanVec","age","duration","previous","empvarrate")).setOutputCol("features")
    val assembler = vectorAssembler.transform(poutcomeEncoded)
    println("assembler...")
    assembler.select("age","job","marital","default","features").show(false)

    // 对目标变量进行StringIndex特征转换，输出新列label
    val indexerY = new StringIndexer().setInputCol("y").setOutputCol("label")


    //将原始数据selected_Data进行8-2分，80%用于训练数据。20%用于测试数据，评估训练模型的精确度。
    val splits = select_data.randomSplit(Array(.8,.2))
    val training = splits(0).cache()
    val test = splits(1).cache()

    // 实例化逻辑回归算法
    val lr = new LogisticRegression()
    println("LogisticRegression parameters:\n" + lr.explainParams() + "\n")

    // 将特征算法按顺序进行合并，形成一个算法数组
    val transformers = Array(indexer,
                              encoder,
                              maritalIndexer,
                              maritalEncoder,
                              defaultIndexer,
                              defaultEncoder,
                              housingIndexer,
                              housingEncoder,
                              poutcomeIndexer,
                              poutcomeEncoder,
                              loanIndexer,
                              loanEncoder,
                              vectorAssembler,
                              indexerY,lr)
    println("\n迭代测试==========>\n")


    val paramMap = ParamMap(lr.maxIter -> 20)
      .put(lr.maxIter, 100) // Specify 1 Param. This overwrites the original maxIter.
      .put(lr.threshold -> 0.4) // 阈值
      .put(lr.regParam -> 0.0) // 正则化


    // 将算法数组和逻辑回归算法合并，传入pipeline 对象的stages中，然后用作于训练数据，训练模型
    var model = new Pipeline().setStages(transformers).fit(training)

    LogisticRegression.load("")
    println("model stages ...")
    model.stages.foreach{ x =>
      println(x.getClass)
    }
    // 将上一步训练模型作用于测试数据，返回测试结果
    var result = model.transform(test)
    //显示测试结果集中的真实值、预测值、原始值、百分比字段
    result.select("label", "prediction","rawPrediction","probability").show(10,false)

//    result.select("label","prediction","probability").write.format("json").save("D:\\test_data\\spark\\output\\lr\\prob")
    //创建二分类算法评估器，对测试结果进行评估
    val evaluator = new BinaryClassificationEvaluator()

    var aucTraining = evaluator.evaluate(result)

    println("aucTraining = "+aucTraining)


    spark.close()
  }
}
