import play.ebean.sbt.PlayEbean
import play.sbt.PlayImport.PlayKeys
import play.sbt.PlayJava
import sbtassembly.{Assembly, AssemblyPlugin, MergeStrategy}

name := """BusinessDirectory"""

version := "1.0-SNAPSHOT"

//mainClass in assembly := Some("play.core.server.ProdServerStart")


lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean,AssemblyPlugin)


assemblyMergeStrategy in assembly := {
  case x if Assembly.isConfigFile(x) =>
    MergeStrategy.concat
  case sbtassembly.PathList(ps @ _*) if Assembly.isReadme(ps.last) || Assembly.isLicenseFile(ps.last) =>
    MergeStrategy.rename
  case sbtassembly.PathList("META-INF", xs @ _*) =>
    (xs map {_.toLowerCase}) match {
      case ("manifest.mf" :: Nil) | ("index.list" :: Nil) | ("dependencies" :: Nil) =>
        MergeStrategy.discard
      case ps @ (x :: xs) if ps.last.endsWith(".sf") || ps.last.endsWith(".dsa") =>
        MergeStrategy.discard
      case "plexus" :: xs =>
        MergeStrategy.discard
      case "services" :: xs =>
        MergeStrategy.filterDistinctLines
      case ("spring.schemas" :: Nil) | ("spring.handlers" :: Nil) =>
        MergeStrategy.filterDistinctLines
      case _ => MergeStrategy.first

    }
  case _ => MergeStrategy.first
}

scalacOptions in (Compile, doc) += "-no-java-comments"
//sbtassembly.PathList("META-INF", xs @ _*) => MergeStrategy.discard

scalaVersion := "2.12.4"

crossScalaVersions := Seq("2.11.12", "2.12.4")

//retrieveManaged := true

//test in assembly := {} //your static test class array

libraryDependencies += guice
libraryDependencies += jdbc
libraryDependencies += javaJdbc

libraryDependencies += evolutions

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.41"

libraryDependencies ++= Seq(jdbc,cacheApi,"mysql" % "mysql-connector-java" % "5.1.41")

libraryDependencies += "com.microsoft.sqlserver" % "mssql-jdbc" % "6.4.0.jre8"

libraryDependencies += "javax.mail" % "mail" % "1.4"

// https://mvnrepository.com/artifact/com.sendgrid/sendgrid-java
libraryDependencies += "com.sendgrid" % "sendgrid-java" % "2.2.1"

libraryDependencies += "io.vertx" % "vertx-mail-client" % "3.0.0"

// https://mvnrepository.com/artifact/io.vertx/vertx-core
libraryDependencies += "io.vertx" % "vertx-core" % "3.6.2"

// https://mvnrepository.com/artifact/org.avaje.ebeanorm/avaje-ebeanorm-elastic
libraryDependencies += "org.avaje.ebeanorm" % "avaje-ebeanorm-elastic" % "1.3.1"

// https://mvnrepository.com/artifact/org.slf4j/slf4j-api
libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.5"

libraryDependencies += "postgresql" % "postgresql" % "9.1-901-1.jdbc4"

libraryDependencies += "org.mindrot" % "jbcrypt" % "0.3m"

libraryDependencies += "net.sf.flexjson" % "flexjson" % "2.1"
// https://mvnrepository.com/artifact/org.json/json
libraryDependencies += "org.json" % "json" % "20090211"

// https://mvnrepository.com/artifact/org.apache.poi/poi

//scalacOptions in(doc, Compile) += "-no-java-comments"

// https://mvnrepository.com/artifact/com.typesafe.akka/akka-http-jackson
libraryDependencies += "com.typesafe.akka" %% "akka-http-jackson" % "10.0.5"
// https://mvnrepository.com/artifact/com.infobip/infobip-api-java-client
libraryDependencies += "com.infobip" % "infobip-api-java-client" % "2.0.1"


libraryDependencies ++= Seq(
  "be.objectify" %% "deadbolt-java" % "2.6.1"
)

libraryDependencies += "org.apache.poi" % "poi-ooxml" % "3.9"

libraryDependencies += "org.apache.poi" % "poi" % "3.8"

// https://mvnrepository.com/artifact/org.slf4j/slf4j-log4j12
libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.2" % Test

// https://mvnrepository.com/artifact/com.monitorjbl/xlsx-streamer
libraryDependencies += "com.monitorjbl" % "xlsx-streamer" % "2.1.0"

// https://mvnrepository.com/artifact/com.aspose/aspose-cells
//libraryDependencies += "com.aspose" % "aspose-cells" % "8.6.2"

// https://mvnrepository.com/artifact/org.hibernate/hibernate-core
libraryDependencies += "org.hibernate" % "hibernate-core" % "3.5.6-Final"

libraryDependencies += "com.h2database" % "h2" % "1.4.192"

//libraryDependencies += "com.typesafe.play" %% "anorm" % "2.4.0" % "provided"

//libraryDependencies += "com.typesafe.play" % "play_2.10" % "2.4.0"
/*
resolvers ++= Seq(
  "Typesafe" at "http://repo.typesafe.com/typesafe/releases/",
  "Java.net Maven2 Repository" at "http://download.java.net/maven/2/"
)
*/
//libraryDependencies += "org.apache.spark" %% "spark-core" % "1.4.1" %"provided"
PlayKeys.devSettings := Seq("play.server.http.port" -> "9001")
PlayKeys.playDefaultPort := 9001



// Testing libraries for dealing with CompletionStage...
libraryDependencies += "org.assertj" % "assertj-core" % "3.6.2" % Test

libraryDependencies += "org.awaitility" % "awaitility" % "2.0.0" % Test
libraryDependencies += "org.xerial" % "sqlite-jdbc" % "3.8.11"
// Make verbose tests
testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))

//mergeStrategy in assembly := mergeStrategy.discard
