package cn.xukai.spark.ansj



import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.mllib.feature.HashingTF
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.classification.LogisticRegressionWithSGD

/**
  * 垃圾邮件判断
  */
object LogisticRegressionSpam {
  def main(args: Array[String]): Unit = {

    //屏蔽不必要的日志显示在终端上
    Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
    Logger.getLogger("org.apache.eclipse.jetty.server").setLevel(Level.OFF)

    val conf = new SparkConf().setAppName("LogisticRegression").setMaster("local")
    val sc = new SparkContext(conf)

    //垃圾邮件
    val spam = sc.textFile("file:///application/spark-2.2.0-bin-hadoop2.7/examples/jars/spam.txt")
    //非垃圾邮件
    val ham = sc.textFile("file:///application/spark-2.2.0-bin-hadoop2.7/examples/jars/normal.txt")

    //创建一个HashingTF实例来把邮件文本映射为包含25000特征的向量
    val tf = new HashingTF(numFeatures = 25000)
    //各邮件都被切分为单词，每个单词被映射为一个特征
    val spamFeatures = spam.map(email => tf.transform(email.split(" ")))
    val hamFeatures = ham.map(email => tf.transform(email.split(" ")))

    //创建LabeledPoint数据集分别存放垃圾邮件(spam)和正常邮件(ham)的例子
    spamFeatures.collect().foreach { x => print(x + " ,") }
    hamFeatures.collect().foreach { x => print(x + " ,") }

    // Create LabeledPoint datasets for positive (spam) and negative (ham) examples.
    val positiveExamples = spamFeatures.map(features => LabeledPoint(1, features))
    val negativeExamples = hamFeatures.map(features => LabeledPoint(0, features))
    val trainingData = positiveExamples.union(negativeExamples)
    trainingData.cache() // 逻辑回归是迭代算法，所以缓存训练数据的RDD

    //使用SGD算法运行逻辑回归
    val lrLearner = new LogisticRegressionWithSGD()
    val model = lrLearner.run(trainingData)

    //以垃圾邮件和正常邮件的例子分别进行测试。
    val posTestExample = tf.transform("O M G GET cheap stuff by sending money to ...".split(" "))
    val negTestExample = tf.transform("Hi Dad, I started studying Spark the other ...".split(" "))

    val posTest1Example = tf.transform("I really wish well to all my friends.".split(" "))
    val posTest2Example = tf.transform("He stretched into his pocket for some money.".split(" "))
    val posTest3Example = tf.transform("He entrusted his money to me.".split(" "))
    val posTest4Example = tf.transform("Where do you keep your money?".split(" "))
    val posTest5Example = tf.transform("She borrowed some money of me.".split(" "))

    //首先使用，一样的HashingTF特征来得到特征向量，然后对该向量应用得到的模型
    println(s"Prediction for positive test example: ${model.predict(posTestExample)}")
    println(s"Prediction for negative test example: ${model.predict(negTestExample)}")

    println(s"posTest1Example for negative test example: ${model.predict(posTest1Example)}")
    println(s"posTest2Example for negative test example: ${model.predict(posTest2Example)}")
    println(s"posTest3Example for negative test example: ${model.predict(posTest3Example)}")
    println(s"posTest4Example for negative test example: ${model.predict(posTest4Example)}")
    println(s"posTest5Example for negative test example: ${model.predict(posTest5Example)}")

    sc.stop()
  }
}


/**
垃圾邮件

Dear sir, I am a Prince in a far kingdom you have not heard of.  I want to send you money via wire transfer so please ...
Get Viagra real cheap!  Send money right away to ...
Oh my gosh you can be really strong too with these drugs found in the rainforest. Get them cheap right now ...
YOUR COMPUTER HAS BEEN INFECTED!  YOU MUST RESET YOUR PASSWORD.  Reply to this email with your password and SSN ...
THIS IS NOT A SCAM!  Send money and get access to awesome stuff really cheap and never have to ...

非垃圾邮件
Dear Spark Learner, Thanks so much for attending the Spark Summit 2014!  Check out videos of talks from the summit at ...
Hi Mom, Apologies for being late about emailing and forgetting to send you the package.  I hope you and bro have been ...
Wow, hey Fred, just heard about the Spark petabyte sort.  I think we need to take time to try it out immediately ...
Hi Spark user list, This is my first question to this list, so thanks in advance for your help!  I tried running ...
Thanks Tom for your email.  I need to refer you to Alice for this one.  I haven't yet figured out that part either ...
Good job yesterday!  I was attending your talk, and really enjoyed it.  I want to try out GraphX ...
Summit demo got whoops from audience!  Had to let you know. --Joe
  */
