package biz.task

import com.avaje.ebean.Ebean
import models.{TaskValue, TaskStatus, EbayTaskResult, EbayAccountFetchTask}
import scala.collection.JavaConversions._

trait TaskRef {
  def getAllTask = Ebean.find(classOf[EbayAccountFetchTask]).where.findList
  def getAllReadyTask = Ebean.find(classOf[EbayAccountFetchTask]).where.eq("taskStatus",0).findList
  def addTask(taskValue: TaskValue) = {
    Ebean.find(classOf[EbayAccountFetchTask]).where.eq("token",taskValue.ebayAuthToken).findUnique match {
      case null =>{
        var ebayAccountTask = new EbayAccountFetchTask
        ebayAccountTask.setAccount(taskValue.ebayAccount)
        ebayAccountTask.setToken(taskValue.ebayAuthToken)
        ebayAccountTask.setEndTimeFrom(taskValue.endTimeFrom)
        ebayAccountTask.setEndTimeTo(taskValue.endTimeTo)
        ebayAccountTask.setCreateTime(taskValue.createTime)
        ebayAccountTask.setTaskStatus(0)
        Ebean.save(ebayAccountTask)
      }
      case _ =>
    }
  }
  def taskStatus(account:String) = {
    account match {
      case "" => null
      case _ => {
        Ebean.find(classOf[EbayAccountFetchTask]).where.eq("account", account).findUnique match {
          case null => null
          case r:EbayAccountFetchTask => TaskStatus(r)
        }
      }
    }
  }
  def allTaskStatus:java.util.List[TaskStatus] = Ebean.find(classOf[EbayAccountFetchTask]).where.findList.toList.map(TaskStatus)

  def updateTaskStatus(account:String,status:String) = {
    val rs = status match {
      case "running" => 1
      case "finish" => 2
      case "ready" => 0
    }
    account match {
      case "" => Ebean.find(classOf[EbayAccountFetchTask]).where.findList.foreach(r=>{r.setTaskStatus(rs); Ebean.update(r)})
      case _ => Ebean.find(classOf[EbayAccountFetchTask]).where.eq("account",account).findUnique match {
        case null =>
        case r:EbayAccountFetchTask => {
          r.setTaskStatus(rs)
          Ebean.update(r)
        }
      }
    }
  }

  def updatePackageResult(result:String) = {
    val rs = result match {
      case "success" => true
      case _ => false
    }
    
    println("rs----------------------------->"+rs)
    
    println("ebayTaskResultstatus======>"+Ebean.find(classOf[EbayTaskResult]).where.findUnique)
    Ebean.find(classOf[EbayTaskResult]).where.findUnique match{
      case null => Ebean.save((new EbayTaskResult).setResult(rs))
      case r:EbayTaskResult => Ebean.update({r.setResult(rs);r})
    }
  }

  def packageStatus = Ebean.find(classOf[EbayTaskResult]).where.findUnique.getResult
}
