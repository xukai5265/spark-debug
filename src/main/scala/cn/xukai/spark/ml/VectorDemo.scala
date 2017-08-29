package cn.xukai.spark.ml

import org.apache.spark.mllib.linalg.Vectors

/**
  * Created by kaixu on 2017/8/22.
  * 向量是Mllib 中的数据类型
  * 向量分为：
  *   密集向量：由数组组成
  *   稀疏向量：由两个平行数组支持
  */
object VectorDemo extends App{
  val vd = Vectors.dense(2,5,8)
  println(vd(1))

  val vs1 = Vectors.sparse(4,Array(0,1,2,3),Array(5,2,3,1))
  println(vs1(0))
  println(vs1(1))
  println(vs1(2))
  println(vs1(3))

  val vs2 = Vectors.sparse(4, Array(0, 2, 1, 3), Array(9, 3, 4, 7))
  println(vs2(0))
  println(vs2(1))
  println(vs2(2))
  println(vs2(3))
  println(vs2)
}
