name := "wiro-test"

version := "1.0"

scalaVersion := "2.12.2"

resolvers += Resolver.sonatypeRepo("releases")

resolvers += Resolver.bintrayRepo("buildo", "maven")

libraryDependencies += "io.buildo" %% "wiro-http-server" % "0.4.0"

addCompilerPlugin(
  "org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full
)