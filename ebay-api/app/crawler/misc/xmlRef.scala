package crawler.misc

trait XmlRef {
  def initXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
  def initNode(nodeKey: String, nodeValue: String, nodeHeaderContent: String = "") = nodeKey.initTags(true, nodeHeaderContent) + nodeValue + nodeKey.initTags(false)
  implicit class XmlLike(x: String) {
    def initTags(isHeader: Boolean, headerContent: String = "") = isHeader match {
      case true =>
        headerContent match {
          case "" => "<" + x + ">"
          case _ => "<" + x + " " + headerContent + ">"
        }
      case false => "</" + x + ">"
    }
    def appendNode(nodeKey: String, nodeValue: String, nodeHeaderContent: String = "") = x + nodeKey.initTags(true, nodeHeaderContent) + nodeValue + nodeKey.initTags(false)
  }
}