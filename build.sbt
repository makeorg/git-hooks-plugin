organization := "org.make"
name := "git-hooks-plugin"
version := "1.0.5-SNAPSHOT"

description :=
  """
    |SBT plugin allowing to easily manage git hooks on a project
  """.stripMargin

scalaVersion := "2.12.4"

sbtPlugin := true

publishMavenStyle := false

licenses += "Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0.html")

developers := List(
  Developer(
    id = "flaroche",
    name = "François LAROCHE",
    email = "fl@make.org",
    url = url("https://github.com/larochef")
  ),
  Developer(
    id = "cpestoury",
    name = "Charley PESTOURY",
    email = "cp@make.org",
    url = url("https://gitlab.com/cpestoury")
  ),
  Developer(
    id = "csalmon-legagneur",
    name = "Colin SALMON-LEGAGNEUR",
    email = "salmonl.colin@gmail.com", 
    url = url("https://gitlab.com/csalmon-")
  ),
  Developer(
    id = "pda",
    name = "Philippe de ARAUJO",
    email = "pa@make.org",
    url = url("https://gitlab.com/philippe.da")
  )
)

bintrayOrganization := Some("make-org")
bintrayRepository := "public"

scalastyleConfig := baseDirectory.value / "scalastyle-config.xml"
