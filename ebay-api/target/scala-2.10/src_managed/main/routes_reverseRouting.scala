// @SOURCE:/home/ycl/playworkspace/ebay-api/conf/routes
// @HASH:f9c6cd138f260665aa5fb6998dd9921d606ac229
// @DATE:Sat Jul 29 17:45:18 CST 2017

import Routes.{prefix => _prefix, defaultPrefix => _defaultPrefix}
import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._


import Router.queryString


// @LINE:16
// @LINE:15
// @LINE:14
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:9
// @LINE:6
package controllers {

// @LINE:9
class ReverseAssets {
    

// @LINE:9
def at(file:String): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[PathBindable[String]].unbind("file", file))
}
                                                
    
}
                          

// @LINE:16
// @LINE:15
// @LINE:14
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:6
class ReverseApplication {
    

// @LINE:15
def getTaskStatus(account:String): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "getTaskStatus/" + implicitly[PathBindable[String]].unbind("account", dynamicString(account)))
}
                                                

// @LINE:13
def initTask(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "initTask")
}
                                                

// @LINE:12
def startTask(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "startTask")
}
                                                

// @LINE:14
def getAllTaskStatus(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "getAllTaskStatus")
}
                                                

// @LINE:16
def getPackageStatus(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "getPackageStatus")
}
                                                

// @LINE:11
def addAccountTask(): Call = {
   Call("POST", _prefix + { _defaultPrefix } + "addAccountFetchTask")
}
                                                

// @LINE:6
def index(): Call = {
   Call("GET", _prefix)
}
                                                
    
}
                          
}
                  


// @LINE:16
// @LINE:15
// @LINE:14
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:9
// @LINE:6
package controllers.javascript {

// @LINE:9
class ReverseAssets {
    

// @LINE:9
def at : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Assets.at",
   """
      function(file) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
   """
)
                        
    
}
              

// @LINE:16
// @LINE:15
// @LINE:14
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:6
class ReverseApplication {
    

// @LINE:15
def getTaskStatus : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.getTaskStatus",
   """
      function(account) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "getTaskStatus/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("account", encodeURIComponent(account))})
      }
   """
)
                        

// @LINE:13
def initTask : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.initTask",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "initTask"})
      }
   """
)
                        

// @LINE:12
def startTask : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.startTask",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "startTask"})
      }
   """
)
                        

// @LINE:14
def getAllTaskStatus : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.getAllTaskStatus",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "getAllTaskStatus"})
      }
   """
)
                        

// @LINE:16
def getPackageStatus : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.getPackageStatus",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "getPackageStatus"})
      }
   """
)
                        

// @LINE:11
def addAccountTask : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.addAccountTask",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "addAccountFetchTask"})
      }
   """
)
                        

// @LINE:6
def index : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.index",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + """"})
      }
   """
)
                        
    
}
              
}
        


// @LINE:16
// @LINE:15
// @LINE:14
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:9
// @LINE:6
package controllers.ref {


// @LINE:9
class ReverseAssets {
    

// @LINE:9
def at(path:String, file:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]), "GET", """ Map static resources from the /public folder to the /assets URL path""", _prefix + """assets/$file<.+>""")
)
                      
    
}
                          

// @LINE:16
// @LINE:15
// @LINE:14
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:6
class ReverseApplication {
    

// @LINE:15
def getTaskStatus(account:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.getTaskStatus(account), HandlerDef(this, "controllers.Application", "getTaskStatus", Seq(classOf[String]), "GET", """""", _prefix + """getTaskStatus/$account<[^/]+>""")
)
                      

// @LINE:13
def initTask(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.initTask(), HandlerDef(this, "controllers.Application", "initTask", Seq(), "GET", """""", _prefix + """initTask""")
)
                      

// @LINE:12
def startTask(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.startTask(), HandlerDef(this, "controllers.Application", "startTask", Seq(), "GET", """""", _prefix + """startTask""")
)
                      

// @LINE:14
def getAllTaskStatus(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.getAllTaskStatus(), HandlerDef(this, "controllers.Application", "getAllTaskStatus", Seq(), "GET", """""", _prefix + """getAllTaskStatus""")
)
                      

// @LINE:16
def getPackageStatus(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.getPackageStatus(), HandlerDef(this, "controllers.Application", "getPackageStatus", Seq(), "GET", """""", _prefix + """getPackageStatus""")
)
                      

// @LINE:11
def addAccountTask(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.addAccountTask(), HandlerDef(this, "controllers.Application", "addAccountTask", Seq(), "POST", """""", _prefix + """addAccountFetchTask""")
)
                      

// @LINE:6
def index(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Seq(), "GET", """ Home page""", _prefix + """""")
)
                      
    
}
                          
}
        
    