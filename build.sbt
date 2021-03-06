organization := "org.make"
name := "git-hooks-plugin"

description :=
  """
    |SBT plugin allowing to easily manage git hooks on a project
  """.stripMargin

scalaVersion := "2.12.12"

sbtPlugin := true

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

scmInfo := Some(ScmInfo(
  browseUrl = url("https://gitlab.com/makeorg/devtools/git-hooks-plugin"),
  connection = "scm:git:git://gitlab.com:makeorg/devtools/git-hooks-plugin.git",
  devConnection = Some("scm:git:ssh://gitlab.com:makeorg/devtools/git-hooks-plugin.git")))

startYear := Some(2017)

organizationHomepage := Some(url("https://make.org"))
homepage := Some(url("https://gitlab.com/makeorg/devtools/git-hooks-plugin"))


scalastyleConfig := baseDirectory.value / "scalastyle-config.xml"
