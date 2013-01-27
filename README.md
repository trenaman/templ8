Templ8
======

A small project used to micro-benchmark the performance of a number of templating technologies.

Engines
-------

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

Usage
-----

To run the benchmarks just type:

   sbt run

Actual output on a MacBook Pro (2012):

       [info]               benchmark    us linear runtime
       [info]              ScalateSSP 441.7 ===============
       [info]         ScalateMustache 599.6 ====================
       [info]         HandlebarsScala 284.6 =========
       [info]                  Play20 262.1 =========
       [info]              Freemarker 107.5 ===
       [info]                Velocity  59.1 ==
       [info]            StringFormat 105.9 ===
       [info]            StringBuffer  21.1 =
       [info]           StringBuilder  20.9 =
       [info] DumbStringConcatenation 864.9 ==============================

Benchmarks
----------

The test have been written using the Google Caliper microbenchmarking framework [http://code.google.com/p/caliper/]. Each test
feeds the tested template engine with a random instance of an *Employee* object. Each rendering run gets a different and randomly
generated instance. Please note that, given the same random seed, the benchmark is perfectly repetable: every run will produce
always the same set of random instances.

TODO: Would be nice to perform the same tests but factoring in concurrency to check how the engines handle contention.

Comments
---------

Velocity seems awesome-fast while Handlebars (v0.0.3-perf) is about 4x slower. The performance hit on a StringBuffer is
negligible while comparing it to a StringBuilder: probably the JVM is smart enough to figure out that the access
to the StringBuffer is thread safe thus allowing to remove all synchronization (escape analysis).

Freemarker is 2x slower than Velocity while is really disappointing to see how slow '.format' is in Scala. Play 2.0
templates are faster then all the Scalate dialects and is at par with Handlebars.scala (!).

Scalate falls below all other engines (even the humble Handlebars!) while rendering SSP and Mustache
