import Dependencies._

ThisBuild / scalaVersion     := "2.11.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.revature"
ThisBuild / organizationName := "synergy"

lazy val root = (
  project in file(".")
).settings(
  assembly / mainClass := Some("app.Cli"),
  assembly / assemblyJarName := "project2.jar",
  name := "project2",
  libraryDependencies ++= Seq(
    spark,
    sparkSql,
    nscalaTime,
    json4s,
    scalaTest % Test
  )
)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}