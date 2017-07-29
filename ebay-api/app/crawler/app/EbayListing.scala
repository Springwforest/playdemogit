package crawler.app

import javax.persistence.Id
import scala.beans.BeanProperty

/**
  * 解析数据集（由于业务逻辑太过复杂，解析工作单独处理）
  */
class EbayListing {
  /**
    * ID
    */
  @Id
  @BeanProperty
  var id:Int = _

  @BeanProperty
  var detailUrl:String = _

  @BeanProperty
  var pageNo: Int = _

  @BeanProperty
  var account:String = _
}