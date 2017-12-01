organization := "org.make"
name := "git-hooks-plugin"
version := "1.0.4-SNAPSHOT"

description :=
  """
    |SBT plugin allowing to easily manage git hooks on a project
  """.stripMargin

scalaVersion := "2.12.2"

sbtPlugin := true

publishMavenStyle := false

licenses += "Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0.html")

bintrayOrganization in bintray := None
bintrayRepository := "make-sbt-plugins"

scalastyleConfig := baseDirectory.value / "scalastyle-config.xml"