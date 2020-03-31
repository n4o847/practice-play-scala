name := "practice-play-scala"
organization := "com.example"

version := "0.1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  jdbc,
  guice,
  evolutions,
  "com.h2database" % "h2" % "1.4.200",
  "org.scalikejdbc" %% "scalikejdbc" % "3.4.1",
  "org.scalikejdbc" %% "scalikejdbc-config" % "3.4.1",
  "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.8.0-scalikejdbc-3.4",
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
)
