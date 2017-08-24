name := "wiro-test"

version := "1.0"

scalaVersion := "2.12.2"

resolvers += Resolver.sonatypeRepo("releases")

resolvers += Resolver.bintrayRepo("buildo", "maven")

resolvers += "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository"

libraryDependencies += "io.buildo" %% "wiro-http-server" % "0.5.2"

libraryDependencies += "com.typesafe.akka" %% "akka-http-testkit" % "10.0.3" % "test"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

addCompilerPlugin(
  "org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full
)



