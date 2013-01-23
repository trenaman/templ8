import sbt._
import Keys._

object Build extends Build {

  lazy val project = Project("root", file(".")).settings(

    // basics
    name := "templ8",
    organization := "org.adehaus",
    version := "1.0.0-SNAPSHOT",
    scalaVersion := "2.9.2",

    // dependencies
    libraryDependencies ++= Seq(
        "com.google.code.java-allocation-instrumenter" % "java-allocation-instrumenter" % "2.0",
        "com.google.code.gson" % "gson" % "1.7.1",
        "com.google.caliper" % "caliper" % "0.5-rc1",
        "org.apache.velocity" % "velocity" % "1.7",
        "com.gilt" % "handlebars" % "0.0.3-perf",
        "org.fusesource.scalate" % "scalate-web_2.9" % "1.6.1",
        "org.freemarker" % "freemarker" % "2.3.19",
        "org.thymeleaf" % "thymeleaf" % "2.0.15"
    ),
    resolvers += "sonatypeSnapshots" at "http://oss.sonatype.org/content/repositories/snapshots",

    // enable forking in run
    fork in run := true,

    // we need to add the runtime classpath as a "-cp" argument to the `javaOptions in run`, otherwise caliper
    // will not see the right classpath and die with a ConfigurationException
    javaOptions in run <++= (fullClasspath in Runtime) map { cp => Seq("-cp", sbt.Build.data(cp).mkString(":"), "-Xmx2G") }
  )
}
