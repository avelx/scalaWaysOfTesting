name := "scalaWaysOfTesting"

version := "0.1"

scalaVersion := "2.13.0"


resolvers ++= Seq(
  "Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases"
)

libraryDependencies ++= Seq("org.mockito" % "mockito-core" % "3.0.0" % Test)
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"