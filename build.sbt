
lazy val projectName = "minimal_play_scalajs_scalatags"
lazy val projectVersion = "0.0.1"

lazy val commonSettings = Seq(
  scalaVersion := "2.12.6",
  organization := projectName
)

lazy val webAppName = projectName
lazy val webAppVersion = projectVersion
lazy val serverName = "server"
lazy val serverVersion = projectVersion
lazy val clientName = "clientJs"
lazy val clientVersion = projectVersion
lazy val sharedName = "shared"
lazy val sharedVersion = projectVersion

lazy val scalaJsDomVersion = "0.9.3"
lazy val scalaTagsVersion = "0.6.7"

lazy val webApp = (project in file("."))
  .settings(
    commonSettings,
    name := webAppName,
    version := webAppVersion,
    scalaJSProjects := Seq(clientJs),
    pipelineStages in Assets := Seq(scalaJSPipeline),
    pipelineStages := Seq(digest, gzip),
    compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
    libraryDependencies ++= Seq(guice, specs2 % Test),
    WebKeys.packagePrefix in Assets := "public/",
    managedClasspath in Runtime += (packageBin in Assets).value
  ).enablePlugins(PlayScala,WebScalaJSBundlerPlugin,SbtWeb)
    .dependsOn(sharedJvm,server)

lazy val server = (project in file(serverName))
  .settings(
    commonSettings,
    name := serverName,
    version := serverVersion,
  ).dependsOn(sharedJvm)

lazy val clientJs = (project in file(clientName))
  .settings(
    commonSettings,
    name := clientName,
    version := clientVersion,
    scalaJSUseMainModuleInitializer := true,
    mainClass := Some("net.andrewresearch.client.MainClient"),
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % scalaJsDomVersion,
      "com.github.karasiq" %%% "scalajs-bootstrap-v4" % "2.3.1",
    ),
    npmDependencies in Compile += "bootstrap" -> "4.1.1",
  ).enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin, ScalaJSWeb).
    dependsOn(sharedJs)

lazy val shared = (crossProject.crossType(CrossType.Pure) in file(sharedName))
  .settings(
    commonSettings,
    libraryDependencies ++= Seq("com.lihaoyi" %%% "scalatags" % scalaTagsVersion)
  )
lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js

