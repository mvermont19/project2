import Dependencies._

ThisBuild / scalaVersion     := "2.11.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.revature"
ThisBuild / organizationName := "synergy"

lazy val root = (project in file("."))
  .settings(
    name := "project2",
    libraryDependencies ++= Seq(
      scalaTest % Test,
      spark,
      sparkSql,
      sparkHive,
      sparkMl,
      sparkMlLocal
    )
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
