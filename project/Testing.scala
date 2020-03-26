import sbt.Keys._
import sbt._
import BuildKeys._

object Testing {

  import Configs._

  private lazy val testSettings = Seq(
    fork in Test := false,
    parallelExecution in Test := false,
    testOptions in Test += Tests
      .Argument(TestFrameworks.ScalaCheck, "-verbosity", "2")
  )

  private lazy val e2eSettings =
    inConfig(EndToEndTest)(Defaults.testSettings) ++ Seq(
      fork in EndToEndTest := false,
      parallelExecution in EndToEndTest := false,
      scalaSource in EndToEndTest := baseDirectory.value / "src/endToEnd/scala"
    )

  private lazy val testAllSettings = Seq(
    testAll := Nil,
    testAll := testAll.dependsOn(EndToEndTest / test).value,
    testAll := testAll.dependsOn(Test / test).value
  )

  lazy val settings = testSettings ++ e2eSettings ++ testAllSettings
}
