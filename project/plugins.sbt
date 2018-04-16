logLevel := Level.Error

// The Lagom plugin
addSbtPlugin("com.lightbend.lagom" % "lagom-sbt-plugin" % "1.4.4")

//code quality plugins
addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "1.0.0")

// Platform Tooling plugin
addSbtPlugin("com.lightbend.rp" % "sbt-reactive-app" % "0.6.0")