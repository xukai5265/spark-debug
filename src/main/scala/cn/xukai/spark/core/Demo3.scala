package cn.xukai.spark.core
import cn.xukai.spark.core.DataFrameDemo.spark
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2017/9/7.
  */
object Demo3 extends App{
  val spark = SparkSession.builder().appName("Demo3").master("spark://192.168.107.128:7077").getOrCreate()
  spark.sparkContext.addJar("D:\\workspace\\sparkutil\\target\\spark.util-1.0-SNAPSHOT.jar")
  val scoreRdd:RDD[(Long,Double)] = spark.sparkContext.parallelize(Seq((1,10),(1,20),(1,30),(2,20),(2,30),(2,90)),2)
  val addressRdd:RDD[(Long,String)] = spark.sparkContext.parallelize(Seq((1,"zhangsan@126.com"),(2,"lisi@126.com")))
  val resRdd = joinScoresWithAddress2(scoreRdd,addressRdd)
  resRdd.collect().foreach(println)

  /**
    *
    * 将每个人的最高分数发送到邮箱中
    * @param scoreRdd      表示 id,分数
    * @param addressRdd    表示 id,邮箱地址
    * @return
    * 例如：
    * score
    *   id    score
    *   1     10
    *   1     20
    *   1     30
    *   2     20
    *   2     50
    *   2     90
    *
    *   address
    *   id    address
    *   1     zhangsan@126.com
    *   2     lisi@126.com
    *
    *   本函数是对两个rdd join 后再计算最高分数
    *   带来的问题是spark 执行大量的shuffle 操作，性能不佳
    */
  def joinScoresWithAddress1(scoreRdd:RDD[(Long,Double)]
                           ,addressRdd:RDD[(Long,String)]):RDD[(Long,(Double,String))]={
    val joinedRdd = scoreRdd.join(addressRdd)
    joinedRdd.reduceByKey((x,y)=> if(x._1>y._1)x else y)
  }

  /**
    * 上个函数的升级
    * 不同：
    *     先计算出每个id 的最高分数，再join 。大大的降低了shuffle 的成本
    * @param scoreRdd
    * @param addressRdd
    * @return
    */
  def joinScoresWithAddress2(scoreRdd:RDD[(Long,Double)]
                            ,addressRdd:RDD[(Long,String)]):RDD[(Long,(Double,String))]={
    val bestScoreRdd = scoreRdd.reduceByKey((x,y) => if(x>y) x else y)
    bestScoreRdd.join(addressRdd)
  }

  spark.close()
}
