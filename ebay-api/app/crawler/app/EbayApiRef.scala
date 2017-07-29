package crawler.app


import crawler.misc.LogEbeanRef
import org.jsoup.Jsoup
import io.FileIO

trait EbayApiRef extends FileIO with LogEbeanRef{
  /**
    * 检测响应页面返回数据的完整性
    * (判断规则: <Ack>Success</Ack> && <ItemsPerPage>?</ItemsPerPage> && <PageNumber>?</PageNumber>)
    * @return 完整 => true 不完整 => false
    */
  def hasRejected(html: String, perPageCount: Int, pageNo: Int): Boolean = {
    val xml = Jsoup.parse(html, "utf-8")
	val parser = (s:Any) => {
          try {
            println("request Success!")
            println("current page: " + pageNo)
            (xml.select("ItemsPerPage").text.trim.toInt != perPageCount && xml.select("PageNumber").text.trim.toInt != pageNo)
          } catch {
            /**
              * ItemsPerPage,PageNumber Tags 解析异常
              */
            case e: Exception =>
              println(e.getStackTrace)
              true
          }
	}
    try {
      xml.select("Ack").text.trim match {
	case "Success" => parser(xml)
        case "Warning" => parser(xml)
        case _ => true
      }
    } catch {
      /**
        * Ack Tags 解析异常
        */
      case e: Exception =>
        println(e.getStackTrace)
        true
    }
  }
  /**
    * 解析响应页面的分页页数
    */
  def checkBoundary(html: String): Int = {
    val xml = Jsoup.parse(html, "utf-8")
    try {
      val page = xml.select("TotalNumberOfPages").text.trim.toInt
      println("parse pageNumber:" + page)
      page
    } catch {
      case e: Exception =>
        println(e.getStackTrace)
        0
    }
  }

  /**
    * 解析响应页面数据
    */
  def parseHtml(html: String, account: String, path: String): List[EbayListing] = {
    html.writeTo(path) match {
      case true => {
        //Ebean code
        addLog(account,path)
      }
      case _ => println("Write files failed!Please check folders, make sure that is allowed to write this file!")
    }
    var rs = new EbayListing
    rs.setAccount(account)
    List[EbayListing](rs)
  }
}
