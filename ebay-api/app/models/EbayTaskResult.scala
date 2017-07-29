package models

import javax.persistence.{Entity, Table, Column}

import scala.beans.BeanProperty

@Entity
@Table(name = "file_package_result")
class EbayTaskResult {
  @Column(name="result")
  @BeanProperty
  var result:Boolean = false
}
