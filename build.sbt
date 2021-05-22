// val scala3Version = "3.0.0-RC3" 

val scala3Version = "3.0.0" 

lazy val root = project

  .in(file("."))

  .settings(
  
    name := "course",
    
    version := "0.1.0",

    scalaVersion := scala3Version,

    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test",

    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3",
    
    libraryDependencies ++= Seq(
       "com.typesafe.akka" %% "akka-actor-typed" % "2.6.13",
       "com.typesafe.akka" %% "akka-slf4j"       % "2.6.13"
    ).map(_.cross(CrossVersion.for3Use2_13))
  
  )
