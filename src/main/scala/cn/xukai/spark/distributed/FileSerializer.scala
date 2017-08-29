package cn.xukai.spark.distributed

import java.io._

/**
  * Created by kaixu on 2017/8/4.
  */
object FileSerializer {
  def writeObjectToFile(obj: Object, file: String) = {
    val fileStream = new FileOutputStream(file)
    val oos = new ObjectOutputStream(fileStream)
    oos.writeObject(obj)
    oos.close()
  }

  def readObjectFromFile(file: String, classLoader: ClassLoader): Object = {
    val fileStream = new FileInputStream(file)
    val ois = new ObjectInputStream(fileStream) {
      override def resolveClass(desc: ObjectStreamClass): Class[_] =
        Class.forName(desc.getName, false, classLoader)
    }
    val obj = ois.readObject()
    ois.close()
    obj
  }

  def main(args: Array[String]): Unit = {
//    val task = new SimpleTask()
//    FileSerializer.writeObjectToFile(task, "D:\\task.ser")
//    ClassManipulator.saveClassFile(task)
    val fileClassLoader = new FileClassLoader()
    val task = FileSerializer.readObjectFromFile("D:\\task.ser",fileClassLoader).asInstanceOf[Task]
    task.run()
  }
}
