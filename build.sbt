lazy val root = (project in file("."))
  .settings(name := "NN Application")
  .aggregate(replacerapi, replacerimpl, restfulbeapi, restfulbeimpl, webgateway)

organization in ThisBuild := "com.buhryk"

version in ThisBuild := "1.0-SNAPSHOT"

scalaVersion in ThisBuild := "2.12.4"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.2.5" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % Test
val playJsonDerivedCodecs = "org.julienrf" %% "play-json-derived-codecs" % "4.0.0"

lazy val replacerapi = (project in file("module-replacer-api"))
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi,
      playJsonDerivedCodecs
    )
  )

lazy val replacerimpl = (project in file("module-replacer-impl"))
  .settings(commonSettings: _*)
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslServer,
      macwire
    )
  ).dependsOn(replacerapi)

lazy val restfulbeapi = (project in file("module-restfulbe-api"))
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi,
      playJsonDerivedCodecs
    )
  )

lazy val restfulbeimpl = (project in file("module-restfulbe-impl"))
  .settings(commonSettings: _*)
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslServer,
      macwire
    )
  ).dependsOn(restfulbeapi, replacerapi)

lazy val webgateway = (project in file("module-web-gateway"))
  .settings(commonSettings: _*)
  .enablePlugins(PlayScala && LagomPlay)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslServer,
      macwire,
      playJsonDerivedCodecs,
      "org.ocpsoft.prettytime" % "prettytime" % "3.2.7.Final",
      "org.webjars" % "foundation" % "6.2.3",
      "org.webjars" % "foundation-icon-fonts" % "d596a3cfb3"
    ),
    httpIngressPaths := Seq("/")
  ).dependsOn(restfulbeapi)

def commonSettings: Seq[Setting[_]] = Seq(
)

//lagomCassandraEnabled in ThisBuild := false
//lagomKafkaEnabled in ThisBuild := false
//lagomUnmanagedServices in ThisBuild += ("elastic-search" -> "http://127.0.0.1:9200")