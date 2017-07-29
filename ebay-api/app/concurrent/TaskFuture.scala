package concurrent

import akka.actor.{Props, ActorSystem}
import biz.task.FetchTask

/**
 * Created by ycl on 17/04/16.
 */
trait TaskFuture {
  /**
    * 注册一个执行者
    * @param systemName 系统名称
    * @param actorName  执行者名称
    * @return
    */
  def registTask(systemName:String,actorName:String) = {
    ActorSystem(systemName).actorOf(Props[FetchTask], name = actorName)
  }
  def getTask(systemName:String,actorName:String) = {
    ActorSystem(systemName).actorOf(Props[FetchTask], name = actorName)
  }
}
