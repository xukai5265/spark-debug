package cn.xukai.spark.core

import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2018/4/4.
  * mycat
  */
object TaskDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("TaskDemo")
      .master("local[*]")
      .getOrCreate()
    val sc = spark.sparkContext
    sc.hadoopConfiguration.set("fs.defaultFS","hdfs://tianxi-ha")
    sc.hadoopConfiguration.set("dfs.nameservices","tianxi-ha")
    sc.hadoopConfiguration.set("dfs.ha.namenodes.tianxi-ha","nn1,nn2")
    sc.hadoopConfiguration.set("dfs.namenode.rpc-address.tianxi-ha.nn1","hadoop-1:8020")
    sc.hadoopConfiguration.set("dfs.namenode.rpc-address.tianxi-ha.nn2","hadoop-2:8020")
    sc.hadoopConfiguration.set("dfs.client.failover.proxy.provider.tianxi-ha","org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider")

    val data = sc.textFile("hdfs://tianxi-ha/test/t1/test")
    println(data.partitions.length)
  }
}
