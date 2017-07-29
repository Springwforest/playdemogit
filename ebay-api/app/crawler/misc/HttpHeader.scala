package crawler.misc

import org.apache.commons.httpclient.Header

class HttpHeader {
  implicit class HeaderLike(headers: Array[Header]) {
    def appendHeader(headerKey: String, headerValue: String) = headers.+:(new Header(headerKey, headerValue))
  }
  def newHeader = Array[Header]()
}
