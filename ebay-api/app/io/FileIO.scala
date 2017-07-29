package io

import java.io.File
import java.io.FileOutputStream

trait FileIO {
  implicit class FileStringLike(fileString: String) {
    def writeTo(path: String) = {
      var file = new File(path)
      try {
        file.exists match {
          case false => file.createNewFile
          case true =>
        }
        var out = new FileOutputStream(file, false)
        out.write(fileString.getBytes("utf-8"))
        out.close
        true
      }catch{
        case e:Exception => println(e.getStackTrace)
          false
      }
    }
  }
}