package cn.xukai.spark.distributed

import java.io.FileOutputStream

/**
  * Created by kaixu on 2017/8/4.
  */
object ClassManipulator {
  def saveClassFile(obj: AnyRef): Unit = {
    val classLoader = obj.getClass.getClassLoader
    val className = obj.getClass.getName
    val classFile = className.replace('.', '/') + ".class"
    val stream = classLoader.getResourceAsStream(classFile)

    // just use the class simple name as the file name
    val outputFile = className.split('.').last + ".class"
    println("outputFile:"+outputFile)
    val fileStream = new FileOutputStream(outputFile)
    var data = stream.read()
    while (data != -1) {
      fileStream.write(data)
      data = stream.read()
    }
    fileStream.flush()
    fileStream.close()
  }
}
