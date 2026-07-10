lazy val microservice = Project(appName, file("."))
  .enablePlugins(play.sbt.PlayScala, SbtDistributablesPlugin)
  .settings(
    majorVersion := 0,
    scalaVersion := "3.7.4",
    libraryDependencies ++= AppDependencies.compile ++ AppDependencies.test,
    excludeDependencies += ExclusionRule("org.lz4", "lz4-java"),
    scalacOptions += "-Wconf:src=routes/.*:s",
    scalacOptions += "-Wconf:msg=unused-imports&src=html/.*:s",
    scalacOptions += "-Wconf:msg=Flag.*repeatedly:s",
    scoverageSettings
  )

lazy val scoverageSettings = {
  import scoverage.ScoverageKeys
  Seq(
    ScoverageKeys.coverageMinimumStmtTotal := 90,
    ScoverageKeys.coverageFailOnMinimum := false,
    ScoverageKeys.coverageHighlighting := true
  )
}

PlayKeys.playDefaultPort := 8352
val appName = "check-eori-number-stub"
scalafmtOnCompile := true
