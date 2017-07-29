package crawler.frame

/**
  * 爬虫采集通用异常描述
  */
class FetchException(fetchUrl: String) extends RuntimeException {
  /**
    * 获取发生异常的URL
    */
  def getFetchUrl = fetchUrl
}

/**
  * 抓取被拒异常描述（如遭受目标系统屏蔽）
  *
  * @author mclaren
  *
  */
case class FetchRejectedException(fetchUrl: String) extends FetchException(fetchUrl)

/**
  * 描述:目标资源已经被抓取过，防止重复抓取同一个URL而浪费爬虫资源
  * @author mclaren
  *
  */
case class ResourceHasAlreadyBeenFetchedException(fetchUrl: String) extends FetchException(fetchUrl)