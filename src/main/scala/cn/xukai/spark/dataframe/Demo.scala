package cn.xukai.spark.dataframe
import org.apache.spark.SparkConf
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

/**
  * Created by kaixu on 2017/10/12.
  */
object Demo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[2]")
    conf.set("spark.sql.warehouse.dir", "file:/D:/spark/warehouse")
    conf.set("spark.sql.shuffle.partitions", "20")
    val sparkSession = SparkSession.builder().appName("RDD to DataFrame")
      .config(conf).getOrCreate()
    //通过代码的方式,设置Spark log4j的级别
    sparkSession.sparkContext.setLogLevel("WARN")
    import sparkSession.implicits._
    //使用 case class 转换 RDD 到 DataFrame
    val peopleDF = rddToDFCase(sparkSession)
//    val peopleDF = rddToDfStructType(sparkSession)
    peopleDF.show()
//    peopleDF.select($"name",$"age").filter($"age">20).show()
//    peopleDF.schema.printTreeString()
//    peopleDF.where($"name"==="xukai").show()
    peopleDF.distinct().show()
  }


  case class Person(name:String,age:Int,a:String,b:String,c:String,d:String,e:String,f:String,g:String,h:String,
                    i:String,j:String,k:String,l:String,m:String,n:String,o:String,p:String,q:String,r:String,
                    s:String,t:String,u:String,v:String,w:String,x:String,y:String,z:String)
  def rddToDFCase(sparkSession:SparkSession):DataFrame={
    import sparkSession.implicits._
    sparkSession.sparkContext.textFile("file:/D:/spark/warehouse/people22.txt",2)
      .map(line => line.split(",")).map(x=> Person(x(0),x(1).trim.toInt,x(2),x(3),x(4),x(5),x(6),x(7),x(8),x(9),x(10),x(11),x(12),x(13),x(14),
      x(15),x(16),x(17),x(18),x(19),x(20),x(21),x(22),x(23),x(24),x(25),x(26),x(27))).toDF()
  }

  // StructType and convert RDD to DataFrame
  def rddToDfStructType(sparkSession:SparkSession):DataFrame={
    val schema = StructType(
      Seq(StructField("name",StringType,true),StructField("age",IntegerType,true))
    )
    val pepoleRdd = sparkSession.sparkContext.textFile("file:/D:/spark/warehouse/people22.txt",2)
      .map(_.split(",")).map(x => Row(x(0),x(1).trim.toInt,x(2),x(3),x(4),x(5),x(6),x(7),x(8),x(9),x(10),x(11),x(12),x(13),x(14),
      x(15),x(16),x(17),x(18),x(19),x(20),x(21),x(22),x(23),x(24),x(25),x(26),x(27)))
    sparkSession.createDataFrame(pepoleRdd,schema)
  }

}
