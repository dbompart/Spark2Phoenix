<h3>Spark2 to Phoenix Example</h3>

<br>This repo contains Spark code that will read data from Phoenix table and write data to phoenix table. <br>

    - Update the zookeeper hostname
    - BUild project (mvn clean install)
    - Use sqlline.py and create table called TABLE1 and upsert couple of rows
       /usr/hdp/current/phoenix-client/bin/sqlline.py localhost:2181:/hbase-unsecure
       CREATE TABLE TABLE1 (ID BIGINT NOT NULL PRIMARY KEY, COL1 VARCHAR);
       UPSERT INTO TABLE1 (ID, COL1) VALUES (1, 'test_row_1');
       UPSERT INTO TABLE1 (ID, COL1) VALUES (2, 'test_row_2');
 
    - Copy hbase-site.xml to /etc/spark2/conf
    - Usage:
      spark-submit --conf "spark.executor.extraClassPath=/usr/hdp/current/phoenix-client/phoenix-4.7.0.2.6.5.0-292-spark2.jar:/usr/hdp/current/phoenix-client/phoenix-client.jar" --conf "spark.driver.extraClassPath=/usr/hdp/current/phoenix-client/phoenix-4.7.0.2.6.5.0-292-spark2.jar:/usr/hdp/current/phoenix-client/phoenix-client.jar" --class com.hortonworks.support.SparkPhoenix.SparkPhoenixSave /tmp/SparkPhoenix-0.0.1.jar
 