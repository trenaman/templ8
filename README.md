A small project used to micro-benchmark the performance of a number of templating technologies.
-----------------------------------------------------------------------------------------------

Benchmarked engines so far:

* Apache Velocity
* Freemarker
* handlebars.scala
* Scala StringFormat
* Java StringBuilder
* Java StringBuffer
* Java dumb String concatenation

TODO:
* Scalate SSP
* Scalate Mustache
* Scalate SCAML
* Play 2.1 templates

Use sbt to build and run.

[info]  0% Scenario{vm=java, trial=0, benchmark=VelocityRendering, memory=-Xmx1G} 76046.60 ns; ?=5012.02 ns @ 10 trials
[info] 14% Scenario{vm=java, trial=0, benchmark=HandlebarsRendering, memory=-Xmx1G} 258686.40 ns; ?=252375.34 ns @ 10 trials
[info] 29% Scenario{vm=java, trial=0, benchmark=Freemarker, memory=-Xmx1G} 99578.90 ns; ?=9231.84 ns @ 10 trials
[info] 43% Scenario{vm=java, trial=0, benchmark=StringFormat, memory=-Xmx1G} 117928.66 ns; ?=3424.17 ns @ 10 trials
[info] 57% Scenario{vm=java, trial=0, benchmark=StringBuffer, memory=-Xmx1G} 46697.30 ns; ?=646.33 ns @ 10 trials
[info] 71% Scenario{vm=java, trial=0, benchmark=StringBuilder, memory=-Xmx1G} 46286.94 ns; ?=454.09 ns @ 4 trials
[info] 86% Scenario{vm=java, trial=0, benchmark=DumbStringConcatenation, memory=-Xmx1G} 893985.81 ns; ?=19321.75 ns @ 10 trials
[info]
[info]               benchmark    us linear runtime
[info]       VelocityRendering  76.0 ==
[info]     HandlebarsRendering 258.7 ========
[info]              Freemarker  99.6 ===
[info]            StringFormat 117.9 ===
[info]            StringBuffer  46.7 =
[info]           StringBuilder  46.3 =
[info] DumbStringConcatenation 894.0 ==============================
[info]
[info] vm: java
[info] trial: 0
[info] memory: -Xmx1G

Benchmarks
----------

The test are written using the Google Caliper microbenchmarking framework [http://code.google.com/p/caliper/]. Each test
feeds the tested template engine with a random instance of an *Employee* object. Each rendering run gets a different and randomly
generated instance. Please note that, given the same random seed, the benchmark is perfectly repetable: every run will produce
always the same set of random instances.

Comments:
---------

Velocity seems awesome-fast while Handlebars (v0.0.3-perf) is about 4x slower. The performance hit on a StringBuffer is
negligible while comparing it to a StringBuilder: probably the JVM is smart enough to figure out that the access
to the StringBuffer is thread safe thus allowing to remove all synchronization (escape analysis).

Freemarker is slightly slower than Velocity while is really disappointing to see how slow '.format' is in Scala.
