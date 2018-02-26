package cn.xukai.spark.streaming.redis

import java.time.LocalDateTime

import cn.xukai.spark.kafka.KafkaProduceUtil

import scala.util.Random

/**
  * Created by kaixu on 2018/2/26.
  */
object DataProducer extends App{
  private val users = Array("DEIBAH","GLLIEG","HIJMEC","HMGBDE","HIJFLA","JCEBBC","KJLAKG","FHEIKI")
  private val random = new Random()
  val today = LocalDateTime.now()
  val flag = "|~|"
  while(true){
    val line = s"$today${flag}200${flag}/test?pcid=${users(random.nextInt(users.length))}&siteid=${random.nextInt(10)}"
    println(line)
    KafkaProduceUtil.write2kafka("lxw1234",line)
    Thread.sleep(100)
  }
//  val ary:Array[String] = line.split("\\|~\\|",-1)
//  try {
//    val hour = ary(0).substring(0, 13).replace("T", "-")
//    val uri = ary(2).split("[=|&]",-1)
//    val user_id = uri(1)
//    val site_id = uri(3)
//
//    println(s"hour:$hour")
//    println(s"user_id:$user_id")
//    println(s"site_id:$site_id")
//  } catch {
//    case ex : Exception => println(ex.getMessage)
//  }


}
