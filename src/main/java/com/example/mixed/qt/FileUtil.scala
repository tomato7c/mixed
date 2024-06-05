package com.example.mixed.qt

package com.example

import java.io.File

object FileUtil {
  def main(args: Array[String]): Unit = {
    val pattern = """\\"outOrderNo\\":\\"(\d+)\\"""".r
    val str = FileUtil.lineList("path")
      .map(it => {
        pattern.findFirstMatchIn(it) match {
          case Some(s)
          => s.group(1)
          case None => "None"
        }
      })
      .distinct
      .map(_.trim)
      .map(it => s""""${it}"""")
      .size
    println(str)
  }

  def lineList(file: String): List[String] = {
    val source = scala.io.Source.fromFile(file)

    val list = try {
      source.getLines().toList
    } finally {
      source.close()
    }
    list
  }

  def scanDir(path: String, consumer: File => Unit): Unit = {
    def dfs(path: String): Unit = scanDir(path, consumer)

    val file = new File(path)
    if (file.isDirectory) {
      file.listFiles().foreach(it => dfs(it.getAbsolutePath))
    } else {
      val name = file.getName
      if (name.endsWith(".java") || name.endsWith(".kt")) {
        consumer(file)
      }
    }
  }
}