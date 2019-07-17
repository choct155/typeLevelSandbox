import Dependencies._

ThisBuild / scalaVersion     := "2.11.10"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.stronghold"
ThisBuild / organizationName := "stronhold"

lazy val root = (project in file("."))
  .settings(
    name := "typeLevelSandbox",
    fork in run := true, // https://stackoverflow.com/questions/44298847/why-do-we-need-to-add-fork-in-run-true-when-running-spark-sbt-application
    libraryDependencies += "org.scalacheck" % "scalacheck_2.11" % "1.14.0",
    libraryDependencies += "org.typelevel" % "cats-core_2.11" % "2.0.0-M4",
    libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.4.3",
    libraryDependencies += "org.apache.spark" % "spark-sql_2.11" % "2.4.3",
    libraryDependencies += "com.chuusai" % "shapeless_2.11" % "2.3.3",
    libraryDependencies += "org.scalatest" % "scalatest_2.11" % "3.2.0-SNAP10"
  )

// Uncomment the following for publishing to Sonatype.
// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for more detail.

// ThisBuild / description := "Some descripiton about your project."
// ThisBuild / licenses    := List("Apache 2" -> new URL("http://www.apache.org/licenses/LICENSE-2.0.txt"))
// ThisBuild / homepage    := Some(url("https://github.com/example/project"))
// ThisBuild / scmInfo := Some(
//   ScmInfo(
//     url("https://github.com/your-account/your-project"),
//     "scm:git@github.com:your-account/your-project.git"
//   )
// )
// ThisBuild / developers := List(
//   Developer(
//     id    = "Your identifier",
//     name  = "Your Name",
//     email = "your@email",
//     url   = url("http://your.url")
//   )
// )
// ThisBuild / pomIncludeRepository := { _ => false }
// ThisBuild / publishTo := {
//   val nexus = "https://oss.sonatype.org/"
//   if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
//   else Some("releases" at nexus + "service/local/staging/deploy/maven2")
// }
// ThisBuild / publishMavenStyle := true
