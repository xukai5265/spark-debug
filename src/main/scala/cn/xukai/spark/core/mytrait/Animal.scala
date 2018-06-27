package cn.xukai.spark.core.mytrait

/**
  * Created by kaixu on 2018/6/6.
  * 1. 特质中定义的方法没有{} 则未实现 否则为已经实现
  * 2. 类在继承特质时，必须实现其未实现的方法
  * 3. scala 中可以实现一个类同时继承多个特质
  */
trait Animal {
  // 没有实现
  def speak

  def listen:Unit = {

  }
  def run : Unit = {
    println("I`m running")
  }
}
class People extends Animal{
  override def speak: Unit = {
    println("I`m speaking English")
  }
}
object People extends App{
  val people = new People
  people.speak
  people.listen
  people.run
}
