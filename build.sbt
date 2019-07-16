name := "scalaWaysOfTesting"

version := "0.1"

scalaVersion := "2.13.0"

resolvers ++= Seq("Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases")

libraryDependencies ++= Seq("org.mockito" % "mockito-scala_2.13" % "1.5.11" % Test)

libraryDependencies += "org.mockito" % "mockito-scala-scalatest_2.13" % "1.5.11" % Test

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.8"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % "test"