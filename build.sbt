name := "spray-swagger-sample"

organization := "com.mlh"

version := "0.1.0-SNAPSHOT"

homepage := Some(url("https://github.com/mhamrah/spray-swagger-sample"))

startYear := Some(2013)

scmInfo := Some(
  ScmInfo(
    url("https://github.com/mhamrah/spray-swagger-sample"),
    "scm:git:https://github.com/mhamrah/spray-swagger-sample.git",
    Some("scm:git:git@github.com:mhamrah/spray-swagger-sample.git")
  )
)

/* scala versions and options */
scalaVersion := "2.11.1"

crossScalaVersions := Seq(
/*  "2.9.3-RC1",
  "2.9.2",
  "2.9.1", "2.9.1-1",
  "2.9.0", "2.9.0-1",
  "2.8.0", "2.8.1", "2.8.2" */
)

// These options will be used for *all* versions.
scalacOptions ++= Seq(
  "-deprecation"
  ,"-unchecked"
  ,"-encoding", "UTF-8"
  ,"-Xlint"
  // "-optimise"   // this option will slow your build
)

scalacOptions ++= Seq(
  "-Yclosure-elim",
  "-Yinline"
)

// These language flags will be used only for 2.10.x.
// Uncomment those you need, or if you hate SIP-18, all of them.
scalacOptions <++= scalaVersion map { sv =>
  if (sv startsWith "2.11") List(
    "-Xverify"
    ,"-feature"
    ,"-language:postfixOps"
    // "-language:reflectiveCalls",
    // "-language:implicitConversions"
    // "-language:higherKinds",
    // "-language:existentials",
    // "-language:experimental.macros",
    // "-language:experimental.dynamics"
  )
  else Nil
}

javacOptions ++= Seq("-Xlint:unchecked", "-Xlint:deprecation")

val akka = "2.3.3"
val spray = "1.3.1-20140423"

/* dependencies */
libraryDependencies ++= Seq (
  "com.github.nscala-time" %% "nscala-time" % "1.2.0"
  // -- testing --
  , "org.scalatest" % "scalatest_2.11" % "2.2.0" % "test"
  // -- Logging --
  ,"ch.qos.logback" % "logback-classic" % "1.1.2"
  ,"com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2"
  // -- Akka --
  ,"com.typesafe.akka" %% "akka-testkit" % akka % "test"
  ,"com.typesafe.akka" %% "akka-actor" % akka
  ,"com.typesafe.akka" %% "akka-slf4j" % akka
  // -- Sql --
  //,"com.typesafe.slick" %% "slick" % "2.0.2"
  // -- Spray --
  ,"io.spray" %% "spray-routing" % spray
  ,"io.spray" %% "spray-client" % spray
  ,"io.spray" %% "spray-testkit" % spray % "test"
  ,"com.gettyimages" %% "spray-swagger" % "0.4.2" excludeAll( ExclusionRule(organization = "org.json4s") )
  // -- json --
  ,"org.json4s" %% "json4s-jackson" % "3.2.10"
  // -- config --
  ,"com.typesafe" % "config" % "1.2.1"
)

/* you may need these repos */
resolvers ++= Seq(
  // Resolver.sonatypeRepo("snapshots")
  // Resolver.typesafeRepo("releases")
  "spray repo" at "http://repo.spray.io"
)

//atmosSettings

packageArchetype.java_server

seq(Revolver.settings: _*)

net.virtualvoid.sbt.graph.Plugin.graphSettings
