# HistogramIntervals
a program that defines a class Histogram that keeps a count of the numbers in the intervals specified as constructor arguments (the intervals are of the form [3, 5.5), [7, 9.3)), in other words, closed on the left and open on the right and the intervals might have gaps between them, but must not overlap, and are potentially given in an unsorted order) to the Histogram class constructor.

The class must handle out-of-range values as well as flag potential input errors in the interval definitions (e.g., invalid intervals, overlapping intervals).

In addition to populating the intervals, the Histogram class must also keep the overall current sample mean and sample variance for the numbers it has seen so far (i.e., the valid ones that belong to one of the intervals) as well as provide methods to retrieve these values in a thread-safe way.

This class must provide methods to print out the histogram and must be designed to work with double precision floating point numbers.

A typical input to a test program using the Histogram class can be as follows:

the histogram intervals

[3, 4.1)
[8.5, 8.7)
[0, 1.1)
[31.5, 41.27)

the samples to be binned:

40.1
8.1
8.2
30
31.51
1
41.27

the results (if I am printing out the histogram, including the count of samples in each interval, the statistics, and the outliers):

[0, 1.1): 1
[3, 4.1): 0
[8.5, 8.7): 0
[31.5, 41.27): 2

sample mean: 24.203
sample variance: 422.243

outliers: 8.1, 8.2, 30, 41.27

Note that the overall class design must also be thread-safe. In other words, once an instance of the Histogram class is created, insertion operations might occur simultaneously from multiple threads and requests for the current mean and variance might also be requested while more values are still being inserted.

In addition to this multithreading requirement, it is important that the Histogram class be able to manage the intervals using efficient internal data structures (i.e., such that insert operations can be done as efficiently as possible as the number of intervals it manages can be large) as well as efficient algorithms for computing the average and variance.

The following requirements:

(1) it must be an end-to-end program, i.e., an executable that reads the intervals and numbers either from a file or from the command line and print out the resulting histogram and outliers if any

(2) it must include an Ant/Maven file whose targets must include at least: build and test, to build and run the unit tests, respectively.

(3) it must include JUnit tests in Java. The tests must include at least the following scenarios:

(a) a test that demonstrate that erroneous inputs in the histogram definition are flagged

(b) a test where a configurable number of threads is used to read values and insert them into the histogram. This test must be able to compare the state of the histogram produced by multiple threads to the sequential execution, thus asserting that the parallel execution is correct. Specifically, you must demonstrate via this test that multiple threads can simultaneously insert samples in the histogram (perhaps by having each inserter thread reading from different files).

(4) assume this is production-grade (you may want to consider running a linter over you code (e.g., FindBugs/CheckStyle), so formatting, error handling (on the input data and elsewhere where needed), and documentation (Javadoc) are all important
