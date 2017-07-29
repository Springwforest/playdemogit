package crawler.frame

import java.io.IOException
import java.net.{BindException, SocketException, SocketTimeoutException, UnknownHostException}
import java.util.concurrent.RecursiveTask

import io.InternetIO
import org.apache.commons.httpclient.{ConnectTimeoutException, Header}

trait Fetch[T] extends InternetIO {
  private val forkJoinController = new ForkJoinController[T]

  def fork(task:RecursiveTask[List[T]]) = this.forkJoinController.fork(task)

  def waitingForComplete = this.forkJoinController.waitingForComplete

  def postFetch(fetchUrl: String /* URL */ , headers: Array[Header],
                body: String /* requestBody */ ,
                contentFilter: String => String /* content filter */ ,
                parseHtml: String => List[T])(hasRejected: String => Boolean)(retry:Int=0) /* test if it has been rejected */ : List[T] = {
    val _retry = (msg: String,retry:Int) => {
      println("Fault: " + fetchUrl + " Retry " + msg)
      postFetch(fetchUrl, headers, body, contentFilter, parseHtml)(hasRejected)(retry)
    }
    if(retry <= 3) {
      try {
        val html = contentFilter(sendPost(fetchUrl, headers, body)("utf-8").body)
        //检查是否被屏蔽
        if (hasRejected(html)) throw new FetchRejectedException(fetchUrl)
        parseHtml(html)
      } catch {
        case e: UnknownHostException => _retry(e.getMessage, retry + 1)
        case e: SocketTimeoutException => _retry(e.getMessage, retry + 1)
        case e: SocketException => _retry(e.getMessage, retry + 1)
        case e: ConnectTimeoutException => _retry(e.getMessage, retry + 1)
        case e: IOException => _retry(e.getMessage, retry + 1)
        case e: BindException => _retry(e.getMessage, retry + 1)
        case e: FetchRejectedException => List[T]()
        case e: Exception => {
          println("Fetch Unknown exception has been occurred" + e)
          List[T]()
        }
      }
    }else
      List[T]()
  }
}