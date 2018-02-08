package cn.xukai.spark.core

import org.apache.spark.mllib.linalg.Vectors

/**
  * Created by kaixu on 2018/1/9.
  */
object VectorTest {
  def main(args: Array[String]): Unit = {
    val vd = Vectors.dense(1.0,0.0,3.0)
    println(vd(1))
    println(vd)
    println("---------vs1------------")

    //向量个数，序号，value
    val vs1 = Vectors.sparse(3, Array(0, 1), Array(1.0,3.0))
    println(vs1(0)) //序号访问
    println(vs1(1))
    println(vs1(2))
    println(vs1)

    val vs2 = Vectors.sparse(11, Array(0, 1, 2, 3, 5, 10), Array(9, 3, 5, 7, 20, 33))
    val vs3 = Vectors.sparse(11, Array(0, 1, 2, 3, 4, 5), Array(9, 3, 5, 7, 20, 33))
    val vs4 = Vectors.sparse(32, Array(10,13,15,20,21,31), Array(9, 3, 5, 7, 20, 33))
    println(vs2(0))
    println(vs2(1))
    println(vs2(2))
    println(vs2(3))
    println(vs2(5))
    println(vs2(10))
    println("------------vs3-----------")
    println(vs3(0))
    println(vs3(1))
    println(vs3(2))
    println(vs3(3))
    println(vs3(4))
    println(vs3(5))
    println("-----------vs4------------")
    println(vs4(10))
    println(vs4(13))
    println(vs4(15))
    println(vs4(20))
    println(vs4(21))
    println(vs4(31))
    println("-----------vs5------------")
    val vs5 = Vectors.sparse(32, Array(8,12,16,20,22,25,28,29,31), Array(1.0,1.0,1.0,1.0,1.0,1.0,56.0,261.0,1.1))
    println(vs5(8))
    println(vs5(12))
    println(vs5(16))
    println(vs5(20))
    println(vs5(22))
    println(vs5(25))
    println(vs5(28))
    println(vs5(29))
    println(vs5(31))
    println("-----------------vs6-----------------")
    val vs6 = Vectors.sparse(32, Array(8,12,16,31,20,22,25,28,29), Array(1.0,1.0,1.0,1.0,1.0,1.0,56.0,261.0,1.1))
    println(vs6(29))
  }
}
