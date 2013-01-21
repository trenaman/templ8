A small project used to micro-benchmark the performance of a number of templating technologies, including Apache Velocity, handlebars.scala, Scalate's SSPs, Scala's StringFormat, Java's StringBuilder

Use sbt to build and run.

    >  run
    [info] Running templ8.Runner
    [info] Initializing Velocity
    [info] Initializing Scalate
    [info] Initializing Handlebars
    [info] Initializing StringFormat template
    [info] Initializing FreeMarker
    [info]  0% Scenario{vm=java, trial=0, benchmark=VelocityRendering, memory=-Xmx1G} 863.97 ns; ?=79.49 ns @ 10 trials
    [info] 14% Scenario{vm=java, trial=0, benchmark=HandlebarsRendering, memory=-Xmx1G} 7426.51 ns; ?=957.80 ns @ 10 trials
    [info] 29% Scenario{vm=java, trial=0, benchmark=ScalateRendering, memory=-Xmx1G} 606102500.00 ns; ?=239131332.58 ns @ 10 trials
    [info] 43% Scenario{vm=java, trial=0, benchmark=StringFormat, memory=-Xmx1G} 2963.87 ns; ?=24.71 ns @ 3 trials
    [info] 57% Scenario{vm=java, trial=0, benchmark=StringBuilder, memory=-Xmx1G} 329.49 ns; ?=46.06 ns @ 10 trials
    [info] 71% Scenario{vm=java, trial=0, benchmark=StringBuffer, memory=-Xmx1G} 330.20 ns; ?=10.48 ns @ 10 trials
    [info] 86% Scenario{vm=java, trial=0, benchmark=Freemarker, memory=-Xmx1G} 2674.68 ns; ?=12.31 ns @ 3 trials
    [info]
    [info]           benchmark        ns linear runtime
    [info]   VelocityRendering       864 =
    [info] HandlebarsRendering      7427 =
    [info]    ScalateRendering 606102500 ==============================
    [info]        StringFormat      2964 =
    [info]       StringBuilder       329 =
    [info]        StringBuffer       330 =
    [info]          Freemarker      2675 =

... Note the results above. Velocity seems awesome-fast; Handlebars (v 0.0.3-perf) is about 7x slower; the real surprise is how slow SSPs are. I suspect there might be some misconfiguration / misuse of the API here. StringBuilder is fasted (kinda expected) and StringBuffer is slightly slower (due to synchronization). Strange that Freemarker is significantly slower than Velocity. Also strange to see how slow '.format' is in Scala.
