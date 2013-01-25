Templ8
======

A small project used to micro-benchmark the performance of a number of templating technologies.

Goals
-----

Benchmarked engines so far:

* Apache Velocity
* Freemarker
* Scalate SSP
* Scalate Mustache
* handlebars.scala
* Scala StringFormat
* Java StringBuilder
* Java StringBuffer
* Java dumb String concatenation

TODO:

* Scalate SCAML
* Play 2.1 templates
* JSP

Usage
-----

To run the benchmarks just type:

   sbt run

Actual output on a MacBook Pro (2012):

    [info]                benchmark    us linear runtime
    [info]        VelocityRendering  88.9 ===
    [info]      HandlebarsRendering 350.7 ============
    [info]      ScalateSSPRendering 535.3 ===================
    [info] ScalateMustacheRendering 664.5 ========================
    [info]               Freemarker 180.3 ======
    [info]             StringFormat 131.3 ====
    [info]             StringBuffer  53.8 =
    [info]            StringBuilder  50.7 =
    [info]  DumbStringConcatenation 818.8 ==============================

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

Freemarker is 2x slower than Velocity while is really disappointing to see how slow '.format' is in Scala.

Scalate falls below all other engines (even the humble Handlebars!) while rendering SSP and Mustache
