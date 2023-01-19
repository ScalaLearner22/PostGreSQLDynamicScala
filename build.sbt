ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "Project2"
  )


libraryDependencies += "org.postgresql" % "postgresql" % "42.5.0"
