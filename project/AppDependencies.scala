import play.core.PlayVersion.current
import sbt.*

object AppDependencies {

  val bootstrapVersion = "10.5.0"
  val playVersion      = 30

  val compile: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"       %% s"bootstrap-backend-play-$playVersion" % bootstrapVersion,
    "uk.gov.hmrc"       %% "stub-data-generator"                  % "1.6.0",
    "io.chrisdavenport" %% "cats-scalacheck"                      % "0.3.2"
  )

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"            %% s"bootstrap-test-play-$playVersion" % bootstrapVersion,
    "org.playframework"      %% "play-test"                         % current,
    "org.scalatestplus.play" %% "scalatestplus-play"                % "7.0.2"
  ).map(_ % Test)
}
