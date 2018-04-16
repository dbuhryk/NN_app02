lazy val root = (project in file("."))
  .settings(name := "NN Application")
  .aggregate(replacerapi, replacerimpl, restfulbeapi, restfulbeimpl)

organization in ThisBuild := "com.buhryk"

version in ThisBuild := "1.0-SNAPSHOT"

scalaVersion in ThisBuild := "2.12.4"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.2.5" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % Test
val playJsonDerivedCodecs = "org.julienrf" %% "play-json-derived-codecs" % "4.0.0"

lazy val replacerapi = (project in file("module-replacer-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi,
      playJsonDerivedCodecs
    )
  )

lazy val replacerimpl = (project in file("module-replacer-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslServer,
      macwire
    )
  ).dependsOn(replacerapi)

lazy val restfulbeapi = (project in file("module-restfulbe-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi,
      playJsonDerivedCodecs
    )
  )

lazy val restfulbeimpl = (project in file("module-restfulbe-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslServer,
      macwire
    )
  ).dependsOn(restfulbeapi, replacerapi)


//lagomCassandraEnabled in ThisBuild := false
//lagomKafkaEnabled in ThisBuild := false