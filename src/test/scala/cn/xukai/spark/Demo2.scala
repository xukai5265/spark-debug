package cn.xukai.spark

/**
  * Created by kaixu on 2018/1/2.
  */
object Demo2 {
  def main(args: Array[String]): Unit = {
    val a = Array(1,2,3)
    val b = Array("a","b","c")
    val c = a.zip(b)

    for((x,y)<- c){
      println(x+"---"+y)
    }

    val d = Array("x","y","z")
    val e = c.zip(d)

    println(e.getClass)

    for(((aa,bb),y)<- e){
      println("("+aa + "--" +bb+")"+y)
    }

    val f = Array("a")

    for((x,y)<- d.zip(f)){
      println(x +"---"+y)
    }

  }
}
