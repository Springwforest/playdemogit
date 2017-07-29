package crawler.app

import java.util.concurrent.RecursiveTask

import biz.task.TaskRef
import crawler.frame.{Fetch, Pagination}
import scala.collection.JavaConversions._

trait FetchApp extends EbayApiRef with Pagination with Fetch[EbayListing] with TaskRef{

  def fetchAccount(account:String="",token:String="",startTimeFrom:String="",startTimeTo:String="")(savePath:String) = {
    lazy val fetchUrl = "https://api.ebay.com/ws/api.dll"
//    lazy val fileSavePath = savePath + account + "_page_"
    lazy val perPageCount = 200
    //获取任务列表
    getAllReadyTask.toList.foreach(x=> {
      fork(new RecursiveTask[List[EbayListing]] {
        def compute: List[EbayListing] = {
          println("start task : account=" + x.getAccount)
          updateTaskStatus(x.getAccount,"running")
          //更新任务运行状态
          println("分页边界抓取~~")
          page(/*请求链接*/ fetchUrl,
            /*获取请求头*/ EbayApiRequest.getSellerListHeader,
            /*获取请求内容*/ EbayApiRequest.getSellerListRequest(x.getToken, x.getEndTimeFrom, x.getEndTimeTo, perPageCount, 1),
            /*响应页面过滤*/ x => {x.trim},
            /*解析页面页数*/ checkBoundary)(/*判断响应页面数据的完整性*/ h => hasRejected(h, perPageCount, 1))().map(listPageNo => {
            var listTask = new RecursiveTask[List[EbayListing]] {
              def compute: List[EbayListing] = {
                println("分页数据抓取~~")
                postFetch(/*请求链接*/ fetchUrl,
                  /*获取请求头*/ EbayApiRequest.getSellerListHeader,
                  /*获取请求内容*/ EbayApiRequest.getSellerListRequest(x.getToken, x.getEndTimeFrom, x.getEndTimeTo, perPageCount, listPageNo),
                  /*响应页面过滤*/ x => x.trim,
                  /*响应页面数据解析*/ h => parseHtml(h, x.getAccount, savePath+x.getAccount+"_page_"+listPageNo))(/*判断响应页面数据的完整性*/ h => hasRejected(h, perPageCount, listPageNo))()
                List[EbayListing]()
              }
            }
            listTask.fork
            listTask
          }).foreach(task=>task.join)
          //更新任务完成状态
          updateTaskStatus(x.getAccount,"finish")
          println("update running status : account=" + x.getAccount)
          null
        }
      })

    })

    waitingForComplete
  }
}
