package cn.xukai.spark.dataframe.avro

import org.apache.avro.mapred.{AvroInputFormat, AvroWrapper}
import org.apache.hadoop.io.NullWritable
import org.apache.spark.sql.SparkSession

/**
  * Created by kaixu on 2018/2/6.
  */
object Spark_LoadAvro extends App{
  case class CustomerAddressData(ca_address_sk: Long,
                                 ca_address_id: String,
                                 ca_street_number: String,
                                 ca_street_name: String,
                                 ca_street_type: String,
                                 ca_suite_number: String,
                                 ca_city: String,
                                 ca_county: String,
                                 ca_state: String,
                                 ca_zip: String,
                                 ca_country: String,
                                 ca_gmt_offset: Double,
                                 ca_location_type: String
                                )
  val path = "customeraddress.avro"
  val spark = SparkSession.builder().master("local[*]").appName("Spark_LoadAvro").getOrCreate()
  val sc = spark.sparkContext
  var _rdd = sc.hadoopFile[AvroWrapper[CustomerAddress], NullWritable, AvroInputFormat[CustomerAddress]](path)
  val ddd = _rdd.map(line => new CustomerAddressData(
    line._1.datum().getCaAddressSk,
    line._1.datum().getCaAddressId.toString,
    line._1.datum().getCaStreetNumber.toString,
    line._1.datum().getCaStreetName.toString,
    line._1.datum().getCaStreetType.toString,
    line._1.datum().getCaSuiteNumber.toString,
    line._1.datum().getCaCity.toString,
    line._1.datum().getCaCounty.toString,
    line._1.datum().getCaState.toString,
    line._1.datum().getCaZip.toString,
    line._1.datum().getCaCountry.toString,
    line._1.datum().getCaGmtOffset,
    line._1.datum().getCaLocationType.toString
  ))
  val ds = spark.createDataFrame(ddd)
  ds.show()
  val df = ds.toDF()
  df.createTempView("customer_address")
  spark.sql("select * from customer_address limit 10").show()
}
