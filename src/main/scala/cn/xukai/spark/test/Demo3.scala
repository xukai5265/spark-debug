package cn.xukai.spark.test

import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2018/1/4.
  * spark使用：
      1）当前文件a.text的格式为，请统计每个单词出现的个数、计算第四列每个元素出现的个数
      A,b,c,d
      B,b,f,e
      A,a,c,f

      2）在（url,user）的键值对中，如
      a.text
      127.0.0.1 xiaozhang
      127.0.0.1 xiaoli
      127.0.0.2 wangwu
      127.0.0.3 lisi
      …..
      B.text
      127.0.0.4 lixiaolu
      127.0.0.5 lisi
      127.0.0.3 zhangsan
      每个文件至少有1000万行，请用程序完成一下工作，
      1）各个文件的ip数
      2)出现在b.text而没有出现在a.text的IP
      3)每个user出现的次数以及每个user对应的IP的个数
      4)对应IP数最多的前K个user
  */
object Demo3 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("test").master("local[*]").getOrCreate()
    spark.sparkContext.setLogLevel("WARN")
    val A = spark.read.textFile("D:\\test_data\\scala\\A.txt")
    val B = spark.read.textFile("D:\\test_data\\scala\\B.txt")
    import spark.implicits._
    val tab_a = A.map(lines => lines.split(" ")).map(t => (t(0),t(1)))
    val tab_b = B.map(lines => lines.split(" ")).map(t => (t(0),t(1)))
    tab_a.createOrReplaceTempView("a")
    tab_b.createOrReplaceTempView("b")

    // 1 各个文件的ip数
//    println("各个文件的ip数 ")
//    spark.sql("select count(distinct(_1)) from a").show()
//    spark.sql("select count(distinct(_1)) from b").show()
    // 2. 出现在b.text而没有出现在a.text的IP  a 中没有b 的记录都有哪些？
    // 思路： 1. 先查出b 中所有的ip 2.
    tab_a.show(false)
    tab_b.show(false)

    println("出现在b.text而没有出现在a.text的IP")
    spark.sql("select * from b where _1 not in (select distinct(_1) from a)").show(10,false)

    // 3. 每个user出现的次数以及每个user对应的IP的个数
    println("每个user出现的次数")
    val res3 = spark.sql("select count(distinct(_1)) as ips,_2 as user,count(_2) as times from a where group by _2")
    res3.createOrReplaceTempView("res3")
    res3.show()

    // 4. 对应IP数最多的前K个user
    spark.sql("select * from res3 order by times desc").show(3)
  }
}
