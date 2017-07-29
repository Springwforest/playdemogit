package models

import javax.persistence.{Id, Column, Entity, Table}

import org.joda.time.DateTime

import scala.beans.BeanProperty

@Entity
@Table(name = "ebay_task_logs")
class EbayTaskLogs {
  @Id
  @BeanProperty
  var id:Int = _

  @Column(name="account",nullable=false)
  @BeanProperty
  var account:String = _

  @Column(name = "file_path",nullable=false)
  @BeanProperty
  var filePath:String = _

  @Column(name = "create_time",nullable=false)
  @BeanProperty
  var createTime:DateTime = new DateTime()
}
