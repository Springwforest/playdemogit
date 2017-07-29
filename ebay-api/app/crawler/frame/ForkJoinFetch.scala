package crawler.frame

import java.util.concurrent.RecursiveTask

class FetchTask(fetchUrl:String) extends  RecursiveTask[Unit] {
  def compute = {

  }
}
class ForkJoinController[T] extends ForkJoinTurbo[T]
