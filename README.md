<b>Java Concurrency</b><br />
Java was one of the first languages to make multithreading easily available to developers. Java had multithreading capabilities from the very beginning. Therefore, Java developers often face the problems described above. That is the reason I am writing this trail on Java concurrency. As notes to myself, and any fellow Java developer whom may benefit from it.<br />

<b>[Fork/Join Framework](http://www.javacreed.com/java-fork-join-example/)</b><br />
The Fork/Join Framework makes use of a special kind of thread pool called ForkJoinPool, which differentiates it from the rest. ForkJoinPool implements a work-stealing algorithm and can execute ForkJoinTask objects. The ForkJoinPool maintains a number of threads, which number is typically based on the number of CPUs available. Each thread has a special kind of queue, Deques, where all its tasks are placed. This is quite an important point to understand. The threads do not share a common queue, but each thread has its own queue as shown next.<br />

<b>[ExecutorService vs. Fork/Join Framework vs. Parallel Streams](https://dzone.com/articles/forkjoin-framework-vs-parallel)</b><br />

<b>Performance Test</b><br />

Filter string by number of vowels

|Benchmark|Time|Data Size|
|---|---|---|
|Single Thread| 7.4 s|10_000_000|
|Fork/Join| 3.1 s|10_000_000|
|Parallel Stream| 3.0 s|10_000_000|