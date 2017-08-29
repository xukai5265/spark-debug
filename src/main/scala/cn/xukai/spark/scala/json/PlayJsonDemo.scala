package cn.xukai.spark.scala.json

import play.api.libs.functional.syntax._
import play.api.libs.json._

/**
  * Created by kaixu on 2017/8/3.
  * https://www.iteblog.com/archives/2222.html
  */

object PlayJsonDemo extends App{
  val jsonInfo = """{"website": "www.iteblog.com", "email" : "hadoop@iteblog.com"}"""
  case class BlogInfo(website: String, email: String)
  implicit val WebSiteFormat: Reads[BlogInfo] = (
    (JsPath \ "website").read[String] and
      (JsPath \ "email").read[String]
    ) (BlogInfo)
  println(Json.parse(jsonInfo).as[BlogInfo])
}
