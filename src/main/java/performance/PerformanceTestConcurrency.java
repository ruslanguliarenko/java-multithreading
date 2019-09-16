package performance;


import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import forkjoin.RandomString;
import forkjoin.StringFilter;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@BenchmarkMode({Mode.SampleTime})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 2, jvmArgs = {"-Xms2G", "-Xmx2G"})
@Warmup(iterations = 0)
@Measurement(iterations = 2)
public class PerformanceTestConcurrency {
    public List<String> data;
    public int threshold;
    public Predicate<String> filter;
    public StringFilter stringFilter;
    public ForkJoinPool forkJoinPool = new ForkJoinPool();


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().include(PerformanceTestConcurrency.class.getSimpleName())
                                          .forks(1)
                                          .build();
        new Runner(opt).run();
    }

    @Setup
    public void firstInit() {
        RandomString randomString = new RandomString(2, 10);

        data = Stream.generate(randomString::nextString).limit(10000000L).collect(Collectors.toList());
        filter = string -> string.matches("(.*([AEIOUYaeiouy]).*){2}");
        threshold = data.size() / (Runtime.getRuntime().availableProcessors() * 17);
    }

    @Setup(Level.Invocation)
    public void setup() {
        stringFilter = new StringFilter(data, filter, threshold);
    }

    @Benchmark
    public void testFilterStringParallel() {
        data.parallelStream().filter(filter).collect(Collectors.toList());
    }

    @Benchmark
    public void testFilterString() {
        data.stream().filter(filter).collect(Collectors.toList());
    }

    @Benchmark
    public void testFilterStringForkJoin() {
        forkJoinPool.invoke(stringFilter);
    }
}
