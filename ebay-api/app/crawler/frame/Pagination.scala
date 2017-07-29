package crawler.frame

import java.io.IOException
import java.net.{BindException, SocketException, SocketTimeoutException, UnknownHostException}

import io.InternetIO
import org.apache.commons.httpclient.{ConnectTimeoutException, Header}

trait Pagination extends InternetIO {
  def page(fetchUrl: String, headers: Array[Header],
           body: String, contentFilter: String => String, checkBoundary: String => Int)(hasRejected: String => Boolean)(retry:Int=0): List[Int] = {
    val _retry = (msg: String,retry:Int) => {
      println("Fault:" + fetchUrl + "Retry" + msg)
      page(fetchUrl, headers, body, contentFilter, checkBoundary)(hasRejected)(retry)
    }
    if(retry <= 3) {
      try {
        var rts = List[Int]()
        val html = contentFilter(sendPost(fetchUrl, headers, body)("utf-8").body)
        //检查是否被屏蔽
        if (hasRejected(html)) throw new FetchRejectedException(fetchUrl)
        val value = checkBoundary(html)
        for(i <- 1 to value){
          rts = rts .:: (i)
        }
        rts
      } catch {
        case e: UnknownHostException => _retry(e.getMessage, retry + 1)
        case e: SocketTimeoutException => _retry(e.getMessage, retry + 1)
        case e: SocketException => _retry(e.getMessage, retry + 1)
        case e: ConnectTimeoutException => _retry(e.getMessage, retry + 1)
        case e: IOException => _retry(e.getMessage, retry + 1)
        case e: BindException => _retry(e.getMessage, retry + 1)
        case e: ResourceHasAlreadyBeenFetchedException => {
          println("HasAlreadyBeenFetched")
          List[Int]()
        }
        case e: FetchRejectedException => {
          println("FetchRejected")
          List[Int]()
        }
        case e: Exception => {
          println("Paging Unknown exception has been occurred" + e)
          List[Int]()
        }
      }
    }else List[Int]()
  }
}