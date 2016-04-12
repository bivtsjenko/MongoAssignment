name := """Assignment-3-PlayWithMongo"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"


libraryDependencies ++= Seq(
  ws,
  specs2,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0-RC1" % Test,
  "org.webjars" %% "webjars-play" % "2.4.0-1",
  "org.webjars" % "bootstrap" % "3.1.1-2",
  "org.reactivemongo" %% "play2-reactivemongo" % "0.11.11"
)

coverageExcludedPackages := "<empty>;router\\..*;"


resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"