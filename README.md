A small project used to micro-benchmark the performance of a number of templating technologies, including Apache Velocity, handlebars.scala, Scalate's SSPs, Scala's StringFormat, Java's StringBuilder

Use sbt to build and run.

    > run
    [info] Compiling 1 Scala source to /Users/ade/github/templ8/target/scala-2.9.2/classes...
    [info] Compiling 2 Scala sources to /Users/ade/github/templ8/target/scala-2.9.2/classes...
    [info] Running templ8.Runner
    [info] Initializing Velocity
    [info] Initializing Scalate
    [info] Initializing Handlebars
    [info] Initializing StringFormat template
    [info]  0% Scenario{vm=java, trial=0, benchmark=VelocityRendering, memory=-Xmx1G} 1434.90 ns; ?=66.76 ns @ 10 trials
    [info] 17% Scenario{vm=java, trial=0, benchmark=HandlebarsRendering, memory=-Xmx1G} 289048.16 ns; ?=20927.46 ns @ 10 trials
    [info] 33% Scenario{vm=java, trial=0, benchmark=ScalateRendering, memory=-Xmx1G} 1614544500.00 ns; ?=618722932.18 ns @ 10 trials
    [info] 50% Scenario{vm=java, trial=0, benchmark=StringFormat, memory=-Xmx1G} 4846.37 ns; ?=304.01 ns @ 10 trials
    [info] 67% Scenario{vm=java, trial=0, benchmark=StringBuilder, memory=-Xmx1G} 755.37 ns; ?=29.67 ns @ 10 trials
    [info] 83% Scenario{vm=java, trial=0, benchmark=StringBuffer, memory=-Xmx1G} 901.74 ns; ?=38.24 ns @ 10 trials
    [info]
    [info]           benchmark         ns linear runtime
    [info]   VelocityRendering       1435 =
    [info] HandlebarsRendering     289048 =
    [info]    ScalateRendering 1614544500 ==============================
    [info]        StringFormat       4846 =
    [info]       StringBuilder        755 =
    [info]        StringBuffer        902 =
    [info]
    [info] vm: java
    [info] trial: 0
    [info] memory: -Xmx1G

... Note the results above. Velocity seems awesome-fast; Handlebars (v 0.0.3) is much slower (although this is down to known performance issues with 0.0.3); the real surprise is how slow SSPs are. I suspect there might be some misconfiguration / misuse of the API here.
