
lazy val projectName = "minimal_play_scalajs_scalatags"
lazy val projectVersion = "1.0.0"

lazy val commonSettings = Seq(
  scalaVersion := "2.12.4",
  organization := projectName
)

lazy val serverName = "server"
lazy val serverVersion = projectVersion
lazy val clientName = "client"
lazy val clientVersion = projectVersion
lazy val sharedName = "shared"
lazy val sharedVersion = projectVersion

lazy val scalaJsVersion = "0.9.3"
lazy val scalaTagsVersion = "0.6.7"

lazy val server = (project in file(serverName))
  .settings(
    commonSettings,
    name := serverName,
    version := serverVersion,
    scalaJSProjects := Seq(client),
    pipelineStages in Assets := Seq(scalaJSPipeline),
    pipelineStages := Seq(digest, gzip),
    compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
    libraryDependencies ++= Seq(guice, specs2 % Test),
    WebKeys.packagePrefix in Assets := "public/",
    managedClasspath in Runtime += (packageBin in Assets).value
  ).enablePlugins(PlayScala)
    .disablePlugins(PlayLayoutPlugin)
    .dependsOn(sharedJvm)

lazy val client = (project in file(clientName))
  .settings(
    commonSettings,
    name := clientName,
    version := clientVersion,
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % scalaJsVersion
    )
  ).enablePlugins(ScalaJSPlugin, ScalaJSWeb).
    dependsOn(sharedJs)

lazy val shared = (crossProject.crossType(CrossType.Pure) in file(sharedName))
  .settings(
    commonSettings,
    libraryDependencies ++= Seq("com.lihaoyi" %%% "scalatags" % scalaTagsVersion)
  )
lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js

// loads the server project at sbt startup
onLoad in Global := (onLoad in Global).value andThen {s: State => s"project $serverName" :: s}
