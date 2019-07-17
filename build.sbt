name := "scalaWaysOfTesting"

version := "0.1"

scalaVersion := "2.13.0"

lazy val akkaVersion = "2.5.19"

lazy val scalaTestVersion = "3.0.5"

resolvers ++= Seq("Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases")

libraryDependencies ++= Seq("org.mockito" % "mockito-scala_2.13" % "1.5.11" % Test)

libraryDependencies += "org.mockito" % "mockito-scala-scalatest_2.13" % "1.5.11" % Test

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.8"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % "test"


libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.23"
libraryDependencies += "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.23" % Test
libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.5.23" % Test
