package cn.xukai.spark.core.double_ball

import java.util.Properties

import org.apache.spark.sql.SparkSession

/**
  * 计算相邻两期双色球 蓝球升高/ 降低的比重
  * Created by kaixu on 2018/1/26.
  */
object SJBlue_Count extends App {
  var sheng = 0;
  var jiang = 0;
  var eq = 0;
  val spark = SparkSession.builder().master("local[*]").appName("SJBlue_Count").getOrCreate()
  spark.sparkContext.setLogLevel("OFF")
  import spark.implicits._
  val url = "jdbc:mysql://localhost:3306/crawler-hx"
  val props = new Properties()
  props.put("url", url)
  props.put("driver", "com.mysql.jdbc.Driver")
  props.put("user", "root")
  props.put("password", "123456")
  val doubleBall = spark.read.jdbc(url, "double_ball", props)
  doubleBall.cache()
  doubleBall.createOrReplaceTempView("double_ball")

  val blues = spark.sql("select blue from double_ball order by code desc")
  println("reds partitions size:"+blues.rdd.partitions.size)
  blues.show()
  val jj = blues.coalesce(1)
  val blues2Int = jj.map{x =>
    Integer.parseInt(x.getString(0))
  }

  blues2Int.reduce(compute(_,_))

  println(s"升：$sheng  降:$jiang  等:$eq")



  while(true){
    Thread.sleep(10000)
  }

  /**
    * 思路：判断谁大谁小
    * @param blue1
    * @param blue2
    * @return
    */
  def compute(blue1:Int,blue2:Int): Int ={
    println(s"blue1:$blue1")
    println(s"blue2:$blue2")
    val res = blue1-blue2
    println(s"res:$res")
    if(res>0){
      sheng+=1
    }else if(res < 0){
      jiang+=1
    }else{
      eq+=1
    }
    println("------------------------")
    blue2
  }

}
