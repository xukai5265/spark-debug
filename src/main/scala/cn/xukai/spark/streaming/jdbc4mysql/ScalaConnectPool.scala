package cn.xukai.spark.streaming.jdbc4mysql

import java.sql.{Connection, PreparedStatement, ResultSet}
import java.util.Date

import org.apache.commons.dbcp.BasicDataSource
import org.apache.log4j.Logger

import scala.collection.mutable.ArrayBuffer

/**
  * Created by kaixu on 2017/5/26.
  */
object ScalaConnectPool {
  case class DoubleBall(code:String,blue:String,reds:String)
  val log = Logger.getLogger(ScalaConnectPool.this.getClass)
  var ds:BasicDataSource = null

  def getDataSource={
    if(ds == null){
      ds = new BasicDataSource()
      ds.setUsername("root")
      ds.setPassword("123456")
      ds.setUrl("jdbc:mysql://localhost:3306/crawler-hx")
      ds.setDriverClassName("com.mysql.jdbc.Driver")
      ds.setInitialSize(20)
      ds.setMaxActive(100)
      ds.setMinIdle(50)
      ds.setMaxIdle(100)
      ds.setMaxWait(1000)
      ds.setMinEvictableIdleTimeMillis(5*60*1000)
      ds.setTimeBetweenEvictionRunsMillis(10*60*1000)
      ds.setTestOnBorrow(true)
    }
    ds
  }

  def getConnection : Connection= {
    var connect:Connection = null
    try {
      if(ds != null){
        connect = ds.getConnection
      }else{
        connect = getDataSource.getConnection
      }
    }
    connect
  }
  def shutDownDataSource: Unit=if (ds !=null){ds.close()}

  def closeConnection(rs:ResultSet,ps:PreparedStatement,connect:Connection): Unit ={
    if(rs != null){rs.close}
    if(ps != null){ps.close}
    if(connect != null){connect.close}
  }

  def now(): Date ={
    new Date()
  }

  def query(sql:String): ArrayBuffer[DoubleBall] ={
    val conn = getConnection
    val stmt = conn.createStatement()
    val rs = stmt.executeQuery(sql)
    var result = new ArrayBuffer[DoubleBall]()
    while (rs.next()){
      result+=DoubleBall(rs.getString("code"),rs.getString("blue"),rs.getString("red"))
    }
    stmt.close()
    conn.close()
    result
  }

  /**
    * 保存数据
    * @param conn
    * @param sql   "insert into searchKeyWord (insert_time,keyword,search_count) values (now(),'jjj',22)"
    */
  def save(conn:Connection,sql:String): Unit ={
        conn.setAutoCommit(false)
        val stmt = conn.createStatement()
        stmt.addBatch(sql)
        stmt.executeBatch()
        conn.commit()
        stmt.close()

  }

  def main(args: Array[String]): Unit = {
    val rs = query("select code,blue,red from double_ball order by code desc")
    rs.foreach{ resultSet =>
      println("code :"+resultSet.code)
      println("blue :"+resultSet.blue)
      println("reds :"+resultSet.reds)
    }
    println("is end ...")
  }
}
