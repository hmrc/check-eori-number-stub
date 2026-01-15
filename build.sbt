lazy val microservice = Project(appName, file("."))
  .enablePlugins(play.sbt.PlayScala, SbtDistributablesPlugin)
  .settings(
    majorVersion := 0,
    scalaVersion := "3.7.4",
    libraryDependencies ++= AppDependencies.compile ++ AppDependencies.test,
    scalacOptions += "-Wconf:src=routes/.*:s",
    scalacOptions += "-Wconf:msg=unused-imports&src=html/.*:s",
    scalacOptions += "-Wconf:msg=Flag.*repeatedly:s"
  )

PlayKeys.playDefaultPort := 8352
val appName = "check-eori-number-stub"
scalafmtOnCompile := true
