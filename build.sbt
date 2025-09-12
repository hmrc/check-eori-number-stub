lazy val microservice = Project(appName, file("."))
  .enablePlugins(play.sbt.PlayScala, SbtAutoBuildPlugin, SbtGitVersioning, SbtDistributablesPlugin)
  .settings(
    majorVersion := 0,
    scalaVersion := "2.13.16",
    libraryDependencies ++= AppDependencies.compile ++ AppDependencies.test,
    scalacOptions += "-Wconf:src=routes/.*:s",
    scalacOptions += "-Wconf:cat=unused-imports&src=html/.*:s"
  )
  .settings(resolvers += Resolver.jcenterRepo)

PlayKeys.playDefaultPort := 8352
val appName = "check-eori-number-stub"
scalafmtOnCompile := true
