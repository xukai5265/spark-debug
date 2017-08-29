package cn.xukai.spark.scala

/**
  * Created by kaixu on 2017/8/3.
  * scala 对序列应用函数
  */
object Demo6 extends App{
  /**
    * 最大最小值
    */
  val numbers = Seq(11, 2, 5, 1, 6, 3, 9)
  println(numbers.max) //11
  println(numbers.min) //1

  case class Book(title: String, pages: Int)
  val books = Seq(
    Book("Future of Scala developers", 85),
    Book("Parallel algorithms", 240),
    Book("Object Oriented Programming", 130),
    Book("Mobile Development", 495)
  )
  println(books.maxBy(book => book.pages))
  println(books.minBy(book => book.pages))

  /**
    * filter
    * 获取序列中的奇数
    */
  val number1 = Seq(1,2,3,4,5,6,7,8,9,10)
  println(number1.filter(num => num % 2 != 0).mkString(","))
  // 获取页数大于 120 的书
  println(books.filter(book => book.pages >= 120))
  println(books.filterNot(book => book.pages >= 120))

  // flatten 对集合进行合并
  val abcd = Seq('a', 'b', 'c', 'd')
  val efgj = Seq('e', 'f', 'g', 'h')
  val ijkl = Seq('i', 'j', 'k', 'l')
  val mnop = Seq('m', 'n', 'o', 'p')
  val qrst = Seq('q', 'r', 's', 't')
  val uvwx = Seq('u', 'v', 'w', 'x')
  val yz   = Seq('y', 'z')

  val alphabet = Seq(abcd, efgj, ijkl, mnop, qrst, uvwx, yz)
  println(alphabet)
  println(alphabet.flatten)

  /**
    * 欧拉图函数（Euler Diagram函数）
    * 接下来的操作大家都熟知：差集、交集和并集。
    */
  val num1 = Seq(1, 2, 3, 4, 5, 6)
  val num2 = Seq(4, 5, 6, 7, 8, 9)
  println(num1.diff(num2))
  println(num1.union(num2))
  println(num1.intersect(num2))


  /**
    * map 函数
    * 对序列中的每个元素执行相同的处理
    * 例如对number1 序列中的每个元素进行加1 操作
    */
  println(number1.map(num => num + 1).mkString(","))

  /**
    * flatMap 函数
    * flatMap = map + flatten
    */
  //List(A, a, B, b, C, c, D, d)
  println(abcd.flatMap(ch => List(ch.toUpper, ch)))

  /**
    * 对集合进行条件检查
    */
  val number2 = Seq(3, 7, 2, 9, 6, 5, 1, 4, 2)
  println(number2.forall(n => n < 10))


  /**
    * 对集合进行分组
    */
  val number3 = Seq(3, 7, 2, 9, 6, 5, 1, 4, 2)

  //(List(2, 6, 4, 2), List(3, 7, 9, 5, 1))
  println(number3.partition(n => n % 2 == 0))
}
