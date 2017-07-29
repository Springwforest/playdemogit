package models

import javax.persistence.{Column, Id, Table, Entity}
import misc.AsJson
import scala.beans.BeanProperty

/**
  * Created by qiaominhao on 15/12/25.
  */

case class TaskStatus(task:EbayAccountFetchTask) extends AsJson {
  @BeanProperty
  var account = task.getAccount
  @BeanProperty
  var endTimeFrom = task.getEndTimeFrom
  @BeanProperty
  var endTimeTo = task.getEndTimeTo
  @BeanProperty
  var createTime = task.getCreateTime
  @BeanProperty
  var taskStatus = task.getTaskStatus
}
case class TaskValue(ebayAccount:String,ebayAuthToken:String,endTimeFrom:String,endTimeTo:String,createTime:String)
@Entity
@Table(name="ebay_account_task")
class EbayAccountFetchTask extends AsJson{
  @Id
  var id:Int = _
  /**
    * EBAY账号名称
    */
  @BeanProperty
  @Column(name = "account",nullable=false)
  var account:String = _

  /**
    * EBAY账号token
    */
  @BeanProperty
  @Column(name = "token",nullable=false,columnDefinition = "Text")
  var token:String = _
  /**
    * 有效listing产品时间节点
    */
  @BeanProperty
  @Column(name = "end_time_from",nullable=false)
  var endTimeFrom:String = _
  /**
    * 有效listing产品时间节点
    */
  @BeanProperty
  @Column(name = "end_time_to",nullable=false)
  var endTimeTo:String = _
  /**
    * 任务创建时间
    */
  @BeanProperty
  @Column(name = "create_time",nullable=false)
  var createTime:String = _
  /**
    * 任务状态 (0:就绪 1:运行 2:完成)
    */
  @BeanProperty
  @Column(name="task_status")
  var taskStatus:Int = _
}


