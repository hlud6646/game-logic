import Dependencies._

ThisBuild / scalaVersion     := "2.13.4"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"
ThisBuild / watchTriggeredMessage := Watch.clearScreenOnTrigger

lazy val root = (project in file("."))
  .settings(
    name := "64",
    libraryDependencies ++= Seq(
      scalaTest        % Test,
      "org.typelevel" %% "cats-core" % "2.1.1",
      "com.github.julien-truffaut" %% "monocle-core"  % "3.0.0-M3",
      "com.github.julien-truffaut" %% "monocle-macro" % "3.0.0-M3"
    ),
    maxErrors := 5,
  ).enablePlugins(SbtTwirl)

