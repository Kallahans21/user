name := """ba_crud"""
organization := "com.ba"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.11"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play" % "2.8.8",
  "org.postgresql" % "postgresql" % "42.3.1", // Usar la versión 42.3.1
  "javax.inject" % "javax.inject" % "1"
)

libraryDependencies += guice
libraryDependencies += "com.typesafe.play" %% "play-jdbc" % "2.8.8" // Agregar dependencia JDBC
