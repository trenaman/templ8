A small project used to micro-benchmark the performance of a number of templating technologies, including Apache Velocity, handlebars.scala, Scalate's SSPs, Scala's StringFormat, Java's StringBuilder

Use sbt to build and run.

    > run
    [info] Running templ8.Runner
    [info] Initializing Velocity
    [info] Initializing Scalate
    [info] Initializing Handlebars
    [info] Initializing StringFormat template
    [info] Initializing FreeMarker
    [info]  0% Scenario{vm=java, trial=0, benchmark=VelocityRendering, memory=-Xmx1G} 1566.03 ns; ?=136.09 ns @ 10 trials
    [info] 14% Scenario{vm=java, trial=0, benchmark=HandlebarsRendering, memory=-Xmx1G} 514535.33 ns; ?=124841.96 ns @ 10 trials
    [info] 29% Scenario{vm=java, trial=0, benchmark=ScalateRendering, memory=-Xmx1G} 1006861000.00 ns; ?=519390662.79 ns @ 10 trials
    [info] 43% Scenario{vm=java, trial=0, benchmark=StringFormat, memory=-Xmx1G} 5156.86 ns; ?=1072.01 ns @ 10 trials
    [info] 57% Scenario{vm=java, trial=0, benchmark=StringBuilder, memory=-Xmx1G} 793.86 ns; ?=203.05 ns @ 10 trials
    [info] 71% Scenario{vm=java, trial=0, benchmark=StringBuffer, memory=-Xmx1G} 1279.25 ns; ?=399.54 ns @ 10 trials
    [info] 86% Scenario{vm=java, trial=0, benchmark=Freemarker, memory=-Xmx1G} 5344.19 ns; ?=390.64 ns @ 10 trials
    [info]
    [info]           benchmark         ns linear runtime
    [info]   VelocityRendering       1566 =
    [info] HandlebarsRendering     514535 =
    [info]    ScalateRendering 1006861000 ==============================
    [info]        StringFormat       5157 =
    [info]       StringBuilder        794 =
    [info]        StringBuffer       1279 =
    [info]          Freemarker       5344 =

... Note the results above. Velocity seems awesome-fast; Handlebars (v 0.0.3) is much slower (although this is down to known performance issues with 0.0.3); the real surprise is how slow SSPs are. I suspect there might be some misconfiguration / misuse of the API here.
