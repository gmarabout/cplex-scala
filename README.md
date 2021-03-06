A scala library for IBM ILOG CPLEX. 

This library combines functional programming, mathematical programming and constraint programming allowing to 
model optimization problems with a syntax that is close to the standard presentation of these problems in textbooks and 
scientific papers. For instance, a constraint such as:
 

![](equation.gif)

can be written as:

```scala
    model.add(sum (for (i <- 1 to n) yield a(i) * x(i)) <= c(j))
```

To get up to speed, the easiest way to start with this library is to study the examples:
 
 * src/examples/mp: examples of optimization models based on mathematical programming
 * src/examples/cp: examples of optimization models based on constraint programming

This library has been tested using IBM ILOG CPLEX 12.6.3 and 12.7.0, Scala 2.11.8 and Java JDK 1.8.0_121. If you want 
to play with this library and do not have a license of CPLEX, you can download 
[IBM ILOG CPLEX Optimization Studio Community Edition](https://www-01.ibm.com/software/websphere/products/optimization/cplex-studio-community-edition/)

To build the library install gradle 2.10 and set the environment variable `CPLEX_STUDIO_HOME` (e.g. 
on windows `C:\IBM\ILOG\CPLEX_Studio127`).  

Then do:

```
$ gradle build
```

This will create the scala library in directory `build/libs`.


To run the tests, do:

```
$ gradle test
```

Reports are generated in directory `build/reports/tests`.

To generate the scala docs, do:

```
$ gradle scaladoc
```

The scala documentation is generated in directory `build/docs/scaladoc`.