Templ8
======

A small project used to micro-benchmark the performance of a number of templating technologies.

Goals
-----

Benchmarked engines so far:

* Apache Velocity
* Freemarker
* Play 2.0
* Scalate SSP
* Scalate Mustache
* handlebars.scala
* Scala StringFormat
* Java StringBuilder
* Java StringBuffer
* Java dumb String concatenation

TODO:

* JSP

Usage
-----

To run the benchmarks just type:

   sbt run

Actual output on a MacBook Pro (2012):

       [info]               benchmark    us linear runtime
       [info]                Velocity  91.3 ===
       [info]         HandlebarsScala 352.0 ===========
       [info]                  Play20 336.4 ===========
       [info]              ScalateSSP 486.1 ================
       [info]         ScalateMustache 560.7 ==================
       [info]              Freemarker 130.4 ====
       [info]            StringFormat 159.0 =====
       [info]            StringBuffer  50.4 =
       [info]           StringBuilder  49.2 =
       [info] DumbStringConcatenation 891.0 ==============================

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

Freemarker is slightly slower than Velocity while is really disappointing to see how slow '.format' is in Scala. Play 2.0
templates are faster then all the Scalate dialects and is at par with Handlebars.scala (!).

Scalate falls below all other engines (even the humble Handlebars!) while rendering SSP and Mustache
