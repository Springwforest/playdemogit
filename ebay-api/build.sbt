name := "ebay-api"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  javaEbean,
  "mysql" % "mysql-connector-java" % "5.1.35",
  "commons-httpclient" % "commons-httpclient" % "3.1",
  "org.jsoup" % "jsoup" % "1.8.3",
  cache
)     

play.Project.playScalaSettings
