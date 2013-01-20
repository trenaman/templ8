A small project used to micro-benchmark the performance of a number of templating technologies, including Apache Velocity, handlebars.scala, and Scalate's SSPs

Use sbt to build and run.

    > run
    [info] Compiling 1 Scala source to /Users/ade/github/templ8/target/scala-2.9.2/classes...
    [info] Compiling 2 Scala sources to /Users/ade/github/templ8/target/scala-2.9.2/classes...
    [info] Running templ8.Runner
    [info]  0% Scenario{vm=java, trial=0, benchmark=VelocityRendering, memory=-Xmx1G} 1175.60 ns; ?=168.10 ns @ 10 trials
    [info] 33% Scenario{vm=java, trial=0, benchmark=HandlebarsRendering, memory=-Xmx1G} 263363.21 ns; ?=76125.73 ns @ 10 trials
    [info] 67% Scenario{vm=java, trial=0, benchmark=ScalateRendering, memory=-Xmx1G} 985903500.00 ns; ?=400348494.57 ns @ 10 trials
    [info]
    [info]           benchmark        us linear runtime
    [info]   VelocityRendering      1.18 =
    [info] HandlebarsRendering    263.36 =
    [info]    ScalateRendering 985903.50 ==============================
    [info]
    [info] vm: java
    [info] trial: 0
    [info] memory: -Xmx1G

... Note the results above. Velocity seems awesome-fast; Handlebars (v 0.0.3) is much slower (although this is down to known performance issues with 0.0.3); the real surprise is how slow SSPs are. I suspect there might be some misconfiguration / misuse of the API here.
