package cn.xukai.spark.core.mytrait

/**
  * Created by kaixu on 2018/6/6.
  */
// 轮胎
trait Tire {
  def run:Unit={
    println("I can run fast")
  }
}
//方向盘
trait SteeringWheel{
  def control: Unit ={
    println("I can control the cars'direction")
  }
}

class Roadster extends Tire with SteeringWheel{
  def display():Unit={
    println("I'm a Roadster")
  }
}

// 敞篷跑车
object Roadster extends App{
  val roadster = new Roadster
  roadster.display()
  roadster.run
  roadster.control
}
