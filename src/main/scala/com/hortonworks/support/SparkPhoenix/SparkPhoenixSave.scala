
/** **********************************************************************************************************************
  *
  * This program reads data from a Phoenix table and then writes data to the same Phoenix table.
  *
  * 0) Update the zookeeper hostname
  * Note: Use sqlline.py and create table called TABLE1 and upsert couple of rows
  *
  * 1)  /usr/hdp/current/phoenix-client/bin/sqlline.py localhost:2181:/hbase-unsecure
  * CREATE TABLE TABLE1 (ID BIGINT NOT NULL PRIMARY KEY, COL1 VARCHAR);
  * UPSERT INTO TABLE1 (ID, COL1) VALUES (1, 'test_row_1');
  * UPSERT INTO TABLE1 (ID, COL1) VALUES (2, 'test_row_2');
  *
  * 2) Copy hbase-site.xml to /etc/spark2/conf
  *
  * 3) Usage:
  * spark-submit --conf "spark.executor.extraClassPath=/usr/hdp/current/phoenix-client/phoenix-4.7.0.2.6.5.0-292-spark2.jar:/usr/hdp/current/phoenix-client/phoenix-client.jar" --conf "spark.driver.extraClassPath=/usr/hdp/current/phoenix-client/phoenix-4.7.0.2.6.5.0-292-spark2.jar:/usr/hdp/current/phoenix-client/phoenix-client.jar" --class com.hortonworks.support.SparkPhoenix.SparkPhoenixSave /tmp/SparkPhoenix-0.0.1.jar
  *
  * ***********************************************************************************************************************/

package com.hortonworks.support.SparkPhoenix

import org.apache.spark.sql._
import org.apache.phoenix.spark._


object SparkPhoenixSave {
  def main(args: Array[String]) {

    val spark = SparkSession
      .builder()
      .appName("phoenix-test").master("local")
      .getOrCreate()

    val sc = spark.sparkContext
    val sqlContext = spark.sqlContext

    //Read data from Phoenix table
    val df = sqlContext.load(
      "org.apache.phoenix.spark",
      Map("table" -> "TABLE1", "zkUrl" -> "c221-node4.sandy.com:2181")
    )

    df.show()

    //Write data to Phoenix table
    val dataSet = List((3, "test_row_3"), (4, "test_row_4"), (5, "test_row_5"))

    sc.parallelize(dataSet).saveToPhoenix(
      "TABLE1",
      Seq("ID", "COL1"),
      zkUrl = Some("c221-node4.sandy.com:2181")
    )

    df.show()
  }
}

