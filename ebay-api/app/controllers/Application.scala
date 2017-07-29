package controllers

import concurrent.TaskFuture
import misc.AsJson
import play.Play
import play.api.mvc._
import biz.task.TaskRef
import models.TaskValue

object Application extends Controller with AsJson with TaskRef with misc.R with TaskFuture {
  def index = Action {
    Ok("This is yks-api!")
  }
  /**
    * 添加ebay账号Listing产品抓取任务
    * @return HttpTransmission自定义通讯Json
    */
  def addAccountTask = Action{ implicit request => {
    val json = request.body.asJson.get
    val (ebayAccount:String,ebayAuthToken:String,endTimeFrom:String,endTimeTo:String,createTime:String) = (
      (json \ "ebay_account").as[String],     /*EBAY账号名称*/
      (json \ "ebay_token").as[String],    /*EBAY账号Token*/
      (json \ "end_time_from").as[String],  /*EBAY账号Listing上架时间*/
      (json \ "end_time_to").as[String],    /*EBAY账号Listing下架时间*/
      (json \ "create_time").as[String]       /*任务创建时间*/
      )
    addTask(TaskValue(ebayAccount,ebayAuthToken,endTimeFrom,endTimeTo,createTime))
    Ok(RS(200,"success") asJson)
  }}

  /**
    * 启动ebay帐号listing产品抓取任务
    * @return
    */
  def startTask = Action {
    val taskActor = registTask("MySystem","fetchTask")
    taskActor ! ("start","all",Play.application.configuration.getString("save_folder_path"))
    Ok(RS(200,"success") asJson)
  }

  /**
    * 初始化ebay帐号listing产品抓取任务
    * @return
    */
  def initTask = Action {
    val taskActor = registTask("MySystem","fetchTask")
    taskActor ! ("init","")
    Ok(RS(200,"success") asJson)
  }

  /**
    * 获取单个ebay帐号listing产品抓取任务的任务状态
    * @param account
    * @return
    */
  def getTaskStatus(account:String) = Action {
    Ok(RS(200, taskStatus(account)) asJson)
  }

  /**
    * 获取所有ebay帐号listing产品抓取任务的任务状态
    * @return
    */
  def getAllTaskStatus() = Action {
    Ok(RS(200,allTaskStatus) asJson)
  }

  /**
    * 获取打包状态
    * @return
    */
  def getPackageStatus() = Action {
    Ok(RS(200,packageStatus) asJson)
  }
}
