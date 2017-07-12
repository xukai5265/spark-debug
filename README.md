# spark-debug
## spark 任务 远程debug
- step1. 提交任务
```
spark-submit --class cn.xukai.spark.SimpleApp --master local[4] \
--driver-java-options "-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=9091" \
/application/spark-2.2.0-bin-hadoop2.7/examples/jars/spark.util-1.0-SNAPSHOT.jar
```
当执行完step1 会停留在Listening for transport dt_socket at address: 9091 

- step2. idea 在remote 中添加 ip port 执行debug后， step1 开始继续向下执行到断点位置。
