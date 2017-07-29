package io


import java.io.ByteArrayOutputStream
import java.nio.charset.Charset
import java.util.zip.GZIPInputStream
import scala.io.Source
import org.apache.commons.httpclient.DefaultMethodRetryHandler
import org.apache.commons.httpclient.Header
import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.SimpleHttpConnectionManager
import org.apache.commons.httpclient.methods.GetMethod
import org.apache.commons.httpclient.params.HttpClientParams
import org.apache.commons.httpclient.methods.PostMethod
import org.apache.commons.httpclient.NameValuePair
import org.apache.commons.httpclient.methods.StringRequestEntity

case class HttpReturns(var body: String, var proxy: Proxy)

trait InternetIO {
  var cookie = ""


  protected val proxyThreadLocal = new ThreadLocal[Proxy]

  val cfg = System.getProperty("cookie.cfg")
  if (cfg != null)
    Source.fromFile(cfg).getLines().foreach(line => cookie = line)
  def sendPost(url: String, headers: Array[Header], postBody: String)(charset: String = Charset.defaultCharset.name) = {
    postHttpClient(url, headers, postBody)(charset)
  }
  private def postHttpClient(url: String, headers: Array[Header], postBody: String)(charset: String): HttpReturns = postHttpClient(url, headers.+:(new Header("Cookie", cookie)), x => (), postBody)(charset)
  private def postHttpClient(url: String, headers: Array[Header], f: Header => Unit, postBody: String)(charset: String): HttpReturns = {
    var post: PostMethod = null
    try {
      val client = new HttpClient(new HttpClientParams, new SimpleHttpConnectionManager(true))
      //设置代理
      val returns = new HttpReturns(null, null)
      client.getHttpConnectionManager.getParams.setConnectionTimeout(25000)
      client.getHttpConnectionManager.getParams.setSoTimeout(25000)
      var retryHandler = new DefaultMethodRetryHandler
      retryHandler.setRetryCount(1)
      retryHandler.setRequestSentRetryEnabled(false)

      post = new PostMethod(url)
      post.setMethodRetryHandler(retryHandler)
      post.setRequestHeader("User-Agent", "Mozilla/5.0 (X11 Ubuntu i686; rv:26.0) Gecko/20100101 Firefox/26.0")

      post.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*;q=0.8")

      post.setRequestHeader("Accept-Language", "en-US,en;q=0.5")

      post.setRequestHeader("Connection", "keep-alive")
      headers.foreach(post.setRequestHeader _)
      post.setRequestEntity(new StringRequestEntity(postBody))

      var code = client.executeMethod(post)

      //重定向
      if (code == 302) {
        var directUrl = post.getResponseHeader("Location").getValue

        println("Redirect: " + directUrl)
        postHttpClient(directUrl, post.getResponseHeaders, postBody)(charset)
      } else {
        var encoding = getencoding(post.getResponseHeader("Content-Type").getValue, charset)

        /**
          * 处理GZIP解压缩格式
          */
        var inputstream = post.getResponseBodyAsStream
        var contentEncoding = post.getResponseHeader("Content-Encoding")
        if (contentEncoding != null && (!contentEncoding.getValue.isEmpty)) {
          if (contentEncoding.getValue.indexOf("gzip") != -1) {
            inputstream = new GZIPInputStream(inputstream)
          }
        }

        var out = new ByteArrayOutputStream
        var i = 0
        var bytes = new Array[Byte](1024)

        do {
          i = inputstream.read(bytes)
          if (i != -1)
            out.write(bytes, 0, i)
        } while (i != -1)

        var s = new String(out.toByteArray(), encoding)
        returns.body = s
        println("Fetch url " + url)
        returns
      }
    } catch {
      case e: Throwable => throw e
    } finally {
      if (post != null) post.releaseConnection();
    }
  }
  /**
    * 根据URL抓取网络内容
    */
  def fromUrl(url: String, headers: Array[Header], notFetchUrls: List[String] = List[String]())(charset: String = Charset.defaultCharset().name) = {
    fromHttpClient(url, headers, notFetchUrls)(charset)
  }

  private def fromHttpClient(url: String, headers: Array[Header], notFetchUrls: List[String])(charset: String): HttpReturns =
    fromHttpClient(url, headers.+:(new Header("Cookie", cookie)), x => (), notFetchUrls)(charset)
  /**
    * 通过HTTP client进行抓取
    */
  private def fromHttpClient(url: String, headers: Array[Header], f: Header => Unit, notFetchUrls: List[String])(charset: String): HttpReturns = {
    var get: GetMethod = null
    try {
      val client = new HttpClient(new HttpClientParams, new SimpleHttpConnectionManager(true))
      //设置代理
      val returns = new HttpReturns(null, null)
      client.getHttpConnectionManager.getParams.setConnectionTimeout(25000)
      client.getHttpConnectionManager.getParams.setSoTimeout(25000)
      var retryHandler = new DefaultMethodRetryHandler
      retryHandler.setRetryCount(1)
      retryHandler.setRequestSentRetryEnabled(false)

      get = new GetMethod(url)
      get.setMethodRetryHandler(retryHandler)
      get.setRequestHeader("User-Agent", "Mozilla/5.0 (X11 Ubuntu i686; rv:26.0) Gecko/20100101 Firefox/26.0")

      get.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*;q=0.8")

      get.setRequestHeader("Accept-Language", "en-US,en;q=0.5")

      get.setRequestHeader("Connection", "keep-alive")
      headers.foreach(get.setRequestHeader _)
      headers.foreach(println _)

      var code = client.executeMethod(get)

      get.getResponseHeaders.foreach(f)

      //重定向
      if (code == 302) {
        var directUrl = get.getResponseHeader("Location").getValue
        var isFetch = true
        notFetchUrls.foreach(url => {
          if (directUrl.contains(url))
            isFetch = false
        })
        if (isFetch) {
          println("Redirect Url : " + directUrl)
          fromHttpClient(directUrl, get.getResponseHeaders, notFetchUrls)(charset)
        } else
          null
      } else {
        var encoding = getencoding(get.getResponseHeader("Content-Type").getValue, charset)

        /**
          * 处理GZIP解压缩格式
          */
        var inputstream = get.getResponseBodyAsStream
        var contentEncoding = get.getResponseHeader("Content-Encoding")
        if (contentEncoding != null && !contentEncoding.getValue.isEmpty) {
          if (contentEncoding.getValue.indexOf("gzip") != -1) {
            inputstream = new GZIPInputStream(inputstream)
          }
        }

        var out = new ByteArrayOutputStream
        var i = 0
        var bytes = new Array[Byte](1024)

        do {
          i = inputstream.read(bytes)
          if (i != -1)
            out.write(bytes, 0, i)
        } while (i != -1)

        var s = new String(out.toByteArray(), encoding)
        returns.body = s
        println("Fetch url " + url)
        returns
      }
    } catch {
      case e: Throwable => throw e
    } finally {
      if (get != null) get.releaseConnection();
    }
  }

  private def getencoding(contentType: String, default: String): String = {
    try {
      var cs = default
      contentType.split(";").foreach { part =>
      {
        if (part.toUpperCase().trim().startsWith("CHARSET"))
          cs = part.split("=")(1).trim
      }
      }
      cs
    } catch {
      case e: Exception => default
    }
  }
}

