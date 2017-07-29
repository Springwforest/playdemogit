package crawler.frame

import java.util.concurrent.RecursiveTask
import java.util.concurrent.ForkJoinPool

trait ForkJoinTurbo[T] extends RecursiveTask[List[T]]{
  private var queue = List[RecursiveTask[List[T]]]()

  val pool = new ForkJoinPool(100)

  def fork(task:RecursiveTask[List[T]]) = queue = queue.+:(task)

  override def compute: List[T] = {
    var rs = List[T]()
    queue.foreach(t => t.fork)
    queue.foreach(t => {
      var rt = t.join()
      if(rt != null) rs = rs ::: rt
    })
    rs
  }

  def waitingForComplete = pool.submit(this).get
}
