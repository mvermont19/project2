import sbt._

object Dependencies {
  lazy val spark = "org.apache.spark" %% "spark-core" % "2.4.8"
  lazy val sparkSql = "org.apache.spark" %% "spark-sql" % "2.4.8"
  lazy val sparkHive = "org.apache.spark" %% "spark-hive" % "2.4.8"
  lazy val sparkMl = "org.apache.spark" %% "spark-mllib" % "2.4.8"
  lazy val sparkMlLocal = "org.apache.spark" %% "spark-mllib-local" % "2.4.8"
  lazy val nscalaTime = "com.github.nscala-time" %% "nscala-time" % "2.30.0"
  lazy val json4s = "org.json4s" %% "json4s-jackson" % "4.0.3"
  lazy val log4jCore = "org.apache.logging.log4j" % "log4j-core" % "2.11.0" % Runtime
  lazy val log4jApi = "org.apache.logging.log4j" % "log4j-api" % "2.11.0"
  lazy val log4jScala = "org.apache.logging.log4j" %% "log4j-api-scala" % "11.0"
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.2.9"
}
