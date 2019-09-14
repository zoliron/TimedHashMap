package JMHBenchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;

import Naivemap.NaiveTimedHashMap;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author Ronen Zolicha
 */

public class NaiveMapBM {
    final static int ITER_AMOUNT = 100;

    @State(Scope.Thread)
    public static class MyState {
        public NaiveTimedHashMap.TimedHashMap<Integer, String> map = new NaiveTimedHashMap.TimedHashMap<>();
        @Setup(Level.Trial)
        public void doSetup() {
            for(int i=0;i<ITER_AMOUNT;++i){
                map.put(i, i + "", 10, TimeUnit.SECONDS);
            }
        }
        @TearDown(Level.Trial)
        public void doTearDown() {
        }
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput,Mode.SampleTime,Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    @Threads(value =1)
    @Warmup(iterations = 2)
    @Measurement(iterations = 2)
    @Timeout(time = 20, timeUnit = TimeUnit.SECONDS)
    public void putSingleValue(MyState state) {
        state.map.put(10000,10000+"",1,TimeUnit.SECONDS);
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput,Mode.SampleTime,Mode.AverageTime,Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    @Threads(value =1)
    @Warmup(iterations = 2)
    @Measurement(iterations = 2)
    @Timeout(time = 20, timeUnit = TimeUnit.SECONDS)
    public String getSingleValue(MyState state) {
        return state.map.get(10000);
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput,Mode.SampleTime,Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    @Threads(value =1)
    @Warmup(iterations = 2)
    @Measurement(iterations = 2)
    @Timeout(time = 20, timeUnit = TimeUnit.SECONDS)
    public int getSingleSize(MyState state) {
        return state.map.size();
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput,Mode.SampleTime,Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    @Threads(value = 4)
    @Warmup(iterations = 2)
    @Measurement(iterations = 2)
    @Timeout(time = 20, timeUnit = TimeUnit.SECONDS)
    public void putSingleValueMultiThreads(MyState state) {
        state.map.put(20000,20000+"",1,TimeUnit.SECONDS);
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput,Mode.SampleTime,Mode.AverageTime,Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    @Threads(value = 4)
    @Warmup(iterations = 2)
    @Measurement(iterations = 2)
    @Timeout(time = 20, timeUnit = TimeUnit.SECONDS)
    public String getSingleValueMultiThreads(MyState state) {
        return state.map.get(20000);
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput,Mode.SampleTime,Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    @Threads(value = 4)
    @Warmup(iterations = 2)
    @Measurement(iterations = 2)
    @Timeout(time = 20, timeUnit = TimeUnit.SECONDS)
    public int getSingleSizeMultiThreads(MyState state) {
        return state.map.size();
    }

//    @Benchmark
//    @BenchmarkMode({Mode.Throughput,Mode.SampleTime,Mode.AverageTime})
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    @Fork(value = 1)
//    @Threads(value =1)
//    @Warmup(iterations = 2)
//    @Measurement(iterations = 2)
//    @Timeout(time = 20, timeUnit = TimeUnit.SECONDS)
//    public void putMultiValues(MyState state) {
//        for(int i =0; i<ITER_AMOUNT;i++)
//            state.map.put(i,i+"",1,TimeUnit.SECONDS);
//    }
//
//    @Benchmark
//    @BenchmarkMode({Mode.Throughput,Mode.SampleTime,Mode.AverageTime})
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    @Fork(value = 1)
//    @Threads(value =1)
//    @Warmup(iterations = 2)
//    @Measurement(iterations = 2)
//    @Timeout(time = 20, timeUnit = TimeUnit.SECONDS)
//    public void getMultiValues(MyState state) {
//        for(int i =0; i<ITER_AMOUNT;i++)
//            Optional.ofNullable(state.map.get(i));
//    }
//
//    @Benchmark
//    @BenchmarkMode({Mode.Throughput,Mode.SampleTime,Mode.AverageTime})
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    @Fork(value = 1)
//    @Threads(value =1)
//    @Warmup(iterations = 2)
//    @Measurement(iterations = 2)
//    @Timeout(time = 20, timeUnit = TimeUnit.SECONDS)
//    public void getMultiSize(MyState state) {
//        for(int i =0; i<ITER_AMOUNT;i++)
//            state.map.size();
//    }
//
//    @Benchmark
//    @BenchmarkMode({Mode.Throughput,Mode.SampleTime,Mode.AverageTime})
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    @Fork(value = 1)
//    @Threads(value = 4)
//    @Warmup(iterations = 2)
//    @Measurement(iterations = 2)
//    @Timeout(time = 20, timeUnit = TimeUnit.SECONDS)
//    public void putMultiValuesMultiThreads(MyState state) throws InterruptedException {
//         for(int i=0; i<100;i++)
//                state.map.put(i,i+"",1,TimeUnit.SECONDS);
//
//    }
//
//    @Benchmark
//    @BenchmarkMode({Mode.Throughput,Mode.SampleTime,Mode.AverageTime})
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    @Fork(value = 1)
//    @Threads(value = 4)
//    @Warmup(iterations = 2)
//    @Measurement(iterations = 2)
//    @Timeout(time = 20, timeUnit = TimeUnit.SECONDS)
//    public void getMultiValuesMultiThreads(MyState state)throws InterruptedException {
//            for(int i=0; i<100;i++)
//                Optional.ofNullable(state.map.get(i));
//
//    }

    public static void main(String ...args) throws IOException, RunnerException {
        org.openjdk.jmh.Main.main(args);
    }
}
