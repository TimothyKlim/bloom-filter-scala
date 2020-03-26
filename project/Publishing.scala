import bintray.BintrayKeys._
import sbt.Keys._
import sbt._

object Publishing {

  private lazy val bintraySettings = Seq(
    bintrayOrganization := Some("fcomb"),
    bintrayRepository := "maven"
  )

  private lazy val sharedSettings = Seq(
    publishMavenStyle := true,
    publishArtifact in Test := false,
    pomIncludeRepository := Function.const(false)
  )

  private lazy val generalSettings = Seq(
    homepage := Some(
      url("https://github.com/alexandrnikitin/bloom-filter-scala")
    ),
    licenses := Seq(
      "MIT" -> url(
        "https://github.com/alexandrnikitin/bloom-filter-scala/blob/master/LICENSE"
      )
    ),
    scmInfo := Some(
      ScmInfo(
        url("https://github.com/alexandrnikitin/bloom-filter-scala"),
        "scm:git:git@github.com:alexandrnikitin/bloom-filter-scala.git"
      )
    ),
    developers := List(
      Developer(
        "AlexandrNikitin",
        "Alexandr Nikitin",
        "nikitin.alexandr.a@gmail.com",
        url("https://github.com/alexandrnikitin/")
      )
    )
  )

  lazy val settings = generalSettings ++ sharedSettings ++ bintraySettings

  lazy val noPublishSettings = Seq(
    publish := Nil,
    publishLocal := Nil,
    publishArtifact := false
  )
}
