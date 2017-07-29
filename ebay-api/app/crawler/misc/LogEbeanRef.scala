package crawler.misc

import com.avaje.ebean.Ebean
import models.EbayTaskLogs

trait LogEbeanRef {
  def addLog(account:String,filePath:String) = {
    Ebean.find(classOf[EbayTaskLogs]).where.eq("filePath",filePath).findUnique match {
      case null => {
        var taskLogs = new EbayTaskLogs
        taskLogs.setAccount(account)
        taskLogs.setFilePath(filePath)
        Ebean.save(taskLogs)
      }
      case _ =>
    }
  }
}
