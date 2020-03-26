import com.typesafe.sbt.packager.archetypes.JavaAppPackaging
import pl.project13.scala.sbt.JmhPlugin

Global / onChangedBuildSource := ReloadOnSourceChanges

lazy val root = project
  .in(file("."))
  .aggregate(`bloom-filter`, tests, examples)
  .configs(Configs.all: _*)
  .settings(Settings.root: _*)

lazy val `bloom-filter` = project
  .in(file("bloom-filter"))
  .configs(Configs.all: _*)
  .settings(Settings.`bloom-filter`: _*)

lazy val sandbox = project
  .in(file("sandbox"))
  .dependsOn(`bloom-filter`)
  .configs(Configs.all: _*)
  .settings(Settings.sandbox: _*)

lazy val sandboxApp = project
  .in(file("sandboxApp"))
  .dependsOn(`bloom-filter`)
  .configs(Configs.all: _*)
  .settings(Settings.sandboxApp: _*)
  .enablePlugins(JavaAppPackaging)

lazy val tests = project
  .in(file("tests"))
  .dependsOn(`bloom-filter`, sandbox)
  .configs(Configs.all: _*)
  .settings(Settings.tests: _*)

lazy val benchmarks = project
  .in(file("benchmarks"))
  .dependsOn(`bloom-filter`, sandbox)
  .configs(Configs.all: _*)
  .settings(Settings.benchmarks: _*)
  .enablePlugins(JmhPlugin)

lazy val examples = project
  .in(file("examples"))
  .dependsOn(`bloom-filter`)
  .configs(Configs.all: _*)
  .settings(Settings.examples: _*)
  .enablePlugins(JavaAppPackaging)
