import play.core.PlayVersion.current
import play.sbt.PlayImport._
import sbt.Keys.libraryDependencies
import sbt._

object AppDependencies {

  val bootstrapVersion = "7.12.0"

  val compile = Seq(
    "uk.gov.hmrc"             %% "bootstrap-backend-play-28"  % bootstrapVersion,
    "uk.gov.hmrc"             %% "stub-data-generator"        % "1.0.0"
  )

  val test = Seq(
    "uk.gov.hmrc"             %% "bootstrap-test-play-28"   % bootstrapVersion                 % Test,
    "org.scalatest"           %% "scalatest"                % "3.2.12"                 % Test,
    "com.typesafe.play"       %% "play-test"                % current                 % Test,
    "com.vladsch.flexmark"    %  "flexmark-all"             % "0.62.2"               % "test, it",
    "org.scalatestplus.play"  %% "scalatestplus-play"       % "5.1.0"                 % "test, it"
  )
}
