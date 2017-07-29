// @SOURCE:/home/ycl/playworkspace/ebay-api/conf/routes
// @HASH:f9c6cd138f260665aa5fb6998dd9921d606ac229
// @DATE:Sat Jul 29 17:45:18 CST 2017


import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._


import Router.queryString

object Routes extends Router.Routes {

private var _prefix = "/"

def setPrefix(prefix: String) {
  _prefix = prefix
  List[(String,Routes)]().foreach {
    case (p, router) => router.setPrefix(prefix + (if(prefix.endsWith("/")) "" else "/") + p)
  }
}

def prefix = _prefix

lazy val defaultPrefix = { if(Routes.prefix.endsWith("/")) "" else "/" }


// @LINE:6
private[this] lazy val controllers_Application_index0 = Route("GET", PathPattern(List(StaticPart(Routes.prefix))))
        

// @LINE:9
private[this] lazy val controllers_Assets_at1 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("assets/"),DynamicPart("file", """.+""",false))))
        

// @LINE:11
private[this] lazy val controllers_Application_addAccountTask2 = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("addAccountFetchTask"))))
        

// @LINE:12
private[this] lazy val controllers_Application_startTask3 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("startTask"))))
        

// @LINE:13
private[this] lazy val controllers_Application_initTask4 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("initTask"))))
        

// @LINE:14
private[this] lazy val controllers_Application_getAllTaskStatus5 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("getAllTaskStatus"))))
        

// @LINE:15
private[this] lazy val controllers_Application_getTaskStatus6 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("getTaskStatus/"),DynamicPart("account", """[^/]+""",true))))
        

// @LINE:16
private[this] lazy val controllers_Application_getPackageStatus7 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("getPackageStatus"))))
        
def documentation = List(("""GET""", prefix,"""controllers.Application.index"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """assets/$file<.+>""","""controllers.Assets.at(path:String = "/public", file:String)"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """addAccountFetchTask""","""controllers.Application.addAccountTask"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """startTask""","""controllers.Application.startTask"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """initTask""","""controllers.Application.initTask"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """getAllTaskStatus""","""controllers.Application.getAllTaskStatus"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """getTaskStatus/$account<[^/]+>""","""controllers.Application.getTaskStatus(account:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """getPackageStatus""","""controllers.Application.getPackageStatus""")).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
  case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
  case l => s ++ l.asInstanceOf[List[(String,String,String)]] 
}}
      

def routes:PartialFunction[RequestHeader,Handler] = {

// @LINE:6
case controllers_Application_index0(params) => {
   call { 
        invokeHandler(controllers.Application.index, HandlerDef(this, "controllers.Application", "index", Nil,"GET", """ Home page""", Routes.prefix + """"""))
   }
}
        

// @LINE:9
case controllers_Assets_at1(params) => {
   call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        invokeHandler(controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]),"GET", """ Map static resources from the /public folder to the /assets URL path""", Routes.prefix + """assets/$file<.+>"""))
   }
}
        

// @LINE:11
case controllers_Application_addAccountTask2(params) => {
   call { 
        invokeHandler(controllers.Application.addAccountTask, HandlerDef(this, "controllers.Application", "addAccountTask", Nil,"POST", """""", Routes.prefix + """addAccountFetchTask"""))
   }
}
        

// @LINE:12
case controllers_Application_startTask3(params) => {
   call { 
        invokeHandler(controllers.Application.startTask, HandlerDef(this, "controllers.Application", "startTask", Nil,"GET", """""", Routes.prefix + """startTask"""))
   }
}
        

// @LINE:13
case controllers_Application_initTask4(params) => {
   call { 
        invokeHandler(controllers.Application.initTask, HandlerDef(this, "controllers.Application", "initTask", Nil,"GET", """""", Routes.prefix + """initTask"""))
   }
}
        

// @LINE:14
case controllers_Application_getAllTaskStatus5(params) => {
   call { 
        invokeHandler(controllers.Application.getAllTaskStatus, HandlerDef(this, "controllers.Application", "getAllTaskStatus", Nil,"GET", """""", Routes.prefix + """getAllTaskStatus"""))
   }
}
        

// @LINE:15
case controllers_Application_getTaskStatus6(params) => {
   call(params.fromPath[String]("account", None)) { (account) =>
        invokeHandler(controllers.Application.getTaskStatus(account), HandlerDef(this, "controllers.Application", "getTaskStatus", Seq(classOf[String]),"GET", """""", Routes.prefix + """getTaskStatus/$account<[^/]+>"""))
   }
}
        

// @LINE:16
case controllers_Application_getPackageStatus7(params) => {
   call { 
        invokeHandler(controllers.Application.getPackageStatus, HandlerDef(this, "controllers.Application", "getPackageStatus", Nil,"GET", """""", Routes.prefix + """getPackageStatus"""))
   }
}
        
}

}
     