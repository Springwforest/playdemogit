package biz.task

import akka.actor.Actor
import crawler.app.FetchApp
import play.Play
import scala.sys.process._

/**
 * 信号接收处理器
 *
 */
class FetchTask extends Actor with TaskRef with FetchApp {
  def receive = {
    //开启全部准备就绪任务的信号
    case ("start", "all", savePath: String) => {
      import sys.process._
      import scala.collection.JavaConversions._
      //获取任务列表
      //      getAllReadyTask.toList.foreach(x=>{
      //        println("start task : account="+x.getAccount)
      //更新任务运行状态
      //        updateTaskStatus(x.getAccount,"running")
      //执行抓取任务
      fetchAccount()(savePath)
      //tar cf - mydir | pigz >mydir.tgz
      //执行打包命令，程序中调用服务器的shell命令
      //     val rs = (("tar cvfj " + Play.application.configuration.getString("package_path") + " " + Play.application.configuration.getString("save_folder_path")) !)
      //     val rs = (("tar -c " + Play.application.configuration.getString("save_folder_path") + " | pigz >"+ " "  + Play.application.configuration.getString("package_path")) !)
      //        println("package mingling=======>" + ("tar -c " + Play.application.configuration.getString("save_folder_path") + "#" + "|pigz >" + Play.application.configuration.getString("package_path")))
      //tar --use-compress-program=pigz -cvpf ebay-api.tgz --exclude=/ebay-api.tgz /var/lib/mongodb/app/data/ebay-api/
      val rs = (("tar --use-compress-program=pigz -cpf " + Play.application.configuration.getString("package_path") + " " + "--exclude=" + Play.application.configuration.getString("package_path") + " " + Play.application.configuration.getString("save_folder_path")) !)
      println("package mingling=======>" + ("tar --use-compress-program=pigz -cvpf " + Play.application.configuration.getString("package_path") + " " + "--exclude=" + Play.application.configuration.getString("package_path") + " " + Play.application.configuration.getString("save_folder_path")))

      val end = (("echo success" #> new java.io.File("packageResult.txt"))!);
      println("packageResultText----------->" + ("echo hello" #> new java.io.File("packageResult.txt")))

      println("package gz2 result:" + rs)
      //更新打包状态
      updatePackageResult("success")
    }
    //初始化任务状态信号
    case ("init", account: String) => {
      updateTaskStatus(account, "ready")
      updatePackageResult("init")
    }
    //其他信号
    case _ => println("Oh~ I can't understand!")
  }
}
