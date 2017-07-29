package crawler.app

import crawler.misc.{HttpHeader, XmlRef}

object EbayApiRequest extends HttpHeader with XmlRef {
  /**
    * Header Request Args
    */
  lazy val DEVID = "cddef7a0-ded2-4135-bd11-62db8f6939ac"
  lazy val APPID = "Survyc487-9ec7-4317-b443-41e7b9c5bdd"
  lazy val CERTID = "b68855dd-a8dc-4fd7-a22a-9a7fa109196f"
  lazy val GETSELLERLIST = "GetSellerList"
  lazy val COMPATIBILITYLEVEL = "551"
  lazy val SITEID = "0"

  /**
    * Request Body Args
    */
  lazy val URN = "urn:ebay:apis:eBLBaseComponents"
  lazy val GETSELLERLISTREQUEST = "GetSellerListRequest"
  lazy val ERRORLAGUAGE = "en_US"
  lazy val WARNINGLEVEL = "High"
  lazy val GRANULARITYLEVEL = "Coarse"

  def getSellerListHeader = initHeader(DEVID, APPID, CERTID, COMPATIBILITYLEVEL, SITEID, GETSELLERLIST)

  def getSellerListRequest(token: String, startTimeFrom: String, startTimeTo: String, perPageCount: Int, currentPageNo: Int) = initRequest(GETSELLERLISTREQUEST, URN, token, ERRORLAGUAGE, WARNINGLEVEL, GRANULARITYLEVEL, startTimeFrom, startTimeTo, true, true, perPageCount, currentPageNo)

  def initHeader(
      devID: String, 
      appID: String, 
      certID: String, 
      compatibilityLevel: String, 
      siteID: String, 
      apiCallName: String) = newHeader
      .appendHeader("X-EBAY-API-COMPATIBILITY-LEVEL", compatibilityLevel)
      .appendHeader("X-EBAY-API-DEV-NAME", devID)
      .appendHeader("X-EBAY-API-APP-NAME", appID)
      .appendHeader("X-EBAY-API-CERT-NAME", certID)
      .appendHeader("X-EBAY-API-CALL-NAME", apiCallName)
      .appendHeader("X-EBAY-API-SITEID", siteID)

  def initRequest(
      requestTags: String, 
      xmlns: String, 
      token: String, 
      errorLaguage: String, 
      warningLevel: String, 
      granularityLevel: String, 
      startTimeFrom: String, 
      startTimeTo: String, 
      includeVariations: Boolean, 
      includeWatchCount: Boolean, 
      entriesPerPage: Int, 
      pageNumber: Int) = {
    var xml = initXml
    val body = initNode("RequesterCredentials", initNode("eBayAuthToken", token))
    .appendNode("ErrorLanguage", errorLaguage)
    .appendNode("WarningLevel", warningLevel)
    .appendNode("GranularityLevel", granularityLevel)
    .appendNode("EndTimeFrom", startTimeFrom)
    .appendNode("EndTimeTo", startTimeTo)
    .appendNode("IncludeVariations", includeVariations.toString)
    .appendNode("IncludeWatchCount", includeWatchCount.toString)
    .appendNode("Pagination", initNode("EntriesPerPage", entriesPerPage.toString)
    .appendNode("PageNumber", pageNumber.toString))
    xml.appendNode(requestTags, body, "xmlns=\"" + xmlns + "\"")
  }
}
