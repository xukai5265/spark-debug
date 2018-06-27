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
//    println(line)
    KafkaProduceUtil.write2kafka("lxw1234",line,false)
    Thread.sleep(10)
  }

}
