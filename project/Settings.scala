import sbt._
import sbt.Keys._

object Settings {

  val JavaOptions = Seq(
    "-Xmx4G",
    "--add-opens=java.base/jdk.internal.misc=ALL-UNNAMED",
    "--add-exports=java.base/jdk.internal.misc=ALL-UNNAMED",
    "--illegal-access=permit"
  )

  private lazy val build = Seq(
    scalaVersion := "2.13.1",
    crossScalaVersions := Seq("2.12.11"),
    autoCompilerPlugins := true,
    scalacOptions ++= ScalacSettings.base ++ ScalacSettings.specificFor(
      scalaVersion.value
    ),
    javacOptions ++= JavacSettings.base,
    Test / javaOptions ++= JavaOptions,
    organization := "com.github.alexandrnikitin",
    ThisBuild / Compile / doc / sources := Nil,
    ThisBuild / Compile / sources := Nil,
    ThisBuild / Compile / packageDoc / publishArtifact := false
  )

  lazy val root = build ++ Testing.settings ++ Publishing.noPublishSettings
  lazy val `bloom-filter` =
    build ++ Testing.settings ++ Dependencies.`bloom-filter` ++ Publishing.settings ++
      (scalacOptions ++= ScalacSettings.strictBase)
  lazy val sandbox =
    build ++ Testing.settings ++ Dependencies.sandbox ++ Publishing.noPublishSettings
  lazy val sandboxApp =
    build ++ Dependencies.sandboxApp ++ Publishing.noPublishSettings
  lazy val tests =
    build ++ Testing.settings ++ Dependencies.tests ++ Publishing.noPublishSettings
  lazy val benchmarks =
    build ++ Dependencies.benchmarks ++ Publishing.noPublishSettings
  lazy val examples = build ++ Publishing.noPublishSettings

  object JavacSettings {
    val base = Seq("-Xlint")
  }

  object ScalacSettings {
    val base = Seq(
      "-deprecation",
      "-encoding",
      "UTF-8",
      "-feature",
      "-unchecked"
    )

    def specificFor(scalaVersion: String) =
      CrossVersion.partialVersion(scalaVersion) match {
        case _ => Seq("-release", "11")
      }

    val strictBase = Seq(
      // "-Xfatal-warnings",
      "-Xlint",
      "-Ywarn-dead-code",
      "-Ywarn-numeric-widen",
      "-Ywarn-value-discard"
    )
  }

}
