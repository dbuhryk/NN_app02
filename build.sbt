lazy val root = (project in file("."))
  .settings(name := "NN Application")
  .aggregate(module02api, module02impl)

organization in ThisBuild := "com.buhryk"

version in ThisBuild := "1.0-SNAPSHOT"

scalaVersion in ThisBuild := "2.12.4"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.2.5" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % Test
val playJsonDerivedCodecs = "org.julienrf" %% "play-json-derived-codecs" % "4.0.0"

lazy val module02api = (project in file("module02-api"))
  .settings(
  libraryDependencies ++= Seq(
    lagomScaladslApi,
    playJsonDerivedCodecs
  )
)

lazy val module02impl = (project in file("module02-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslServer,
      macwire
    )
  ).dependsOn(module02api)

lagomCassandraEnabled in ThisBuild := false
lagomKafkaEnabled in ThisBuild := false