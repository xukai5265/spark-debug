package cn.xukai.spark.sql

import java.util.Properties

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

/**
  * Created by kaixu on 2017/7/18.
  * http://dblab.xmu.edu.cn/blog/1366-2/
  * spark 获取mysql数据生成dataframe
  */
object Spark2Mysql {
  def main(args: Array[String]): Unit = {
    /*
      step1 mysql 创建数据库及表
        mysql> create database spark;
        mysql> use spark;
        mysql> create table student (id int(4), name char(20), gender char(4), age int(4));
        mysql> insert into student values(1,'Xueqian','F',23);
        mysql> insert into student values(2,'Weiliang','M',24);
        mysql> select * from student;

       step2 启动spark-shell 启动时附加一些参数，指定mysql 驱动的连接jar包
       spark-shell --jars /usr/local/spark/jars/mysql-connector-java-5.1.40/mysql-connector-java-5.1.40-bin.jar \
        --driver-class-path /usr/local/spark/jars/mysql-connector-java-5.1.40/mysql-connector-java-5.1.40-bin.jar
        val jdbcDF = spark.read.format("jdbc").option("url","jdbc:mysql://192.168.107.128:3306/spark").option("driver","com.mysql.jdbc.Driver").option("dbtable", "student").option("user", "root").option("password", "123456").load()
        jdbcDF.show
        +---+--------+------+---+
        | id|    name|gender|age|
        +---+--------+------+---+
        |  1| Xueqian|     F| 23|
        |  2|Weiliang|     M| 24|
        +---+--------+------+---+

     */
    //读取mysql 数据
    val spark = SparkSession
      .builder()
      .appName("spark2mysql")
      .master("spark://192.168.107.128:7077")
      .enableHiveSupport()
      .getOrCreate()
    val prop = new Properties()
    prop.put("user", "root") //表示用户名是root
    prop.put("password", "123456") //表示密码是hadoop
    prop.put("driver", "com.mysql.jdbc.Driver") //表示驱动程序是com.mysql.jdbc.Driver

    val jdbcDF =  spark.read.jdbc("jdbc:mysql://192.168.107.128:3306/spark", "student",prop)
//    val jdbcDF = spark.read.format("jdbc").option("url", "jdbc:mysql://192.168.107.128:3306/spark").option("driver", "com.mysql.jdbc.Driver").option("dbtable", "student").option("user", "root").option("password", "123456").load()
    jdbcDF.show()

    //    //写入mysql 数据
    //    //下面我们设置两条数据表示两个学生信息
    //    val studentRDD = spark.sparkContext.parallelize(Array("3 Rongcheng M 26","4 Guanghua M 24")).map(_.split(" "))
    //
    //    //下面要设置模式信息
    //    val schema = StructType(List(StructField("id", IntegerType, true),StructField("name", StringType, true),StructField("gender", StringType, true),StructField("age", IntegerType, true)))
    //    //下面创建Row对象，每个Row对象都是rowRDD中的一行
    //    val rowRDD = studentRDD.map(p => Row(p(0).toInt, p(1).trim, p(2).trim, p(3).toInt))
    //
    //    //建立起Row对象和模式之间的对应关系，也就是把数据和模式对应起来
    //    val studentDF = spark.createDataFrame(rowRDD, schema)
    //    //下面创建一个prop变量用来保存JDBC连接参数
    //    val prop = new Properties()
    //    prop.put("user", "root") //表示用户名是root
    //    prop.put("password", "123456") //表示密码是hadoop
    //    prop.put("driver","com.mysql.jdbc.Driver") //表示驱动程序是com.mysql.jdbc.Driver
    //    //下面就可以连接数据库，采用append模式，表示追加记录到数据库spark的student表中
    //    studentDF.write.mode("append").jdbc("jdbc:mysql://192.168.107.128:3306/spark", "spark.student", prop)
    spark.close()
  }
}
