package cn.xukai.spark.core.stage

import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2017/12/26.
  * 在这个例子中，假设你需要做如下一些事情：
  *1. 将一个包含人名和地址的文件加载到RDD1中
  *2. 将一个包含人名和电话的文件加载到RDD2中
  *3. 通过name来Join RDD1和RDD2，生成RDD3
  *4. 在RDD3上做Map，给每个人生成一个HTML展示卡作为RDD4
  *5. 将RDD4保存到文件
  *6. 在RDD1上做Map，从每个地址中提取邮编，结果生成RDD5
  *7. 在RDD5上做聚合，计算出每个邮编地区中生活的人数，结果生成RDD6
  *8. Collect RDD6，并且将这些统计结果输出到stdout
  */
object Demo1 {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().appName("").master("local[*]").getOrCreate()
    val lines1 = sparkSession.read.textFile("D:\\test_data\\spark\\stage\\address.txt").rdd
    val lines2 = sparkSession.read.textFile("D:\\test_data\\spark\\stage\\phone.txt").rdd
    val rdd1 = lines1.map{s =>
      val parts= s.split(" ")
      (parts(0),parts(1))
    }
    val rdd2 = lines2.map{ s =>
      val parts = s.split(" ")
      (parts(0),parts(1))
    }
    rdd1.cache()
    rdd2.cache()
    val rdd3 = rdd1.join(rdd2)
    val rdd4 = rdd3.map{s =>
      "姓名："+s._1   + "\n"+
      "邮编："+s._2._1+ "\n"+
      "电话："+s._2._2
    }

    rdd4.saveAsTextFile("D:\\test_data\\spark\\output\\stage\\person1")
    val rdd5 = rdd1.map{ s =>
      s._2
    }

    val rdd6 = rdd5.map(x => (x,1)).reduceByKey(_+_).collect()
    rdd6.foreach(println)

    while(true){
      Thread.sleep(10000)
      println(System.currentTimeMillis())
    }
  }
}
