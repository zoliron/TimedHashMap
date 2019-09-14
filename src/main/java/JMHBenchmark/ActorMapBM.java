package JMHBenchmark;

import Actormap.ActorTimedHaspMap;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;

import BasicMap.MapInterface;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author Ronen Zolicha
 */

public class ActorMapBM {
    final static int ITER_AMOUNT = 100;

    @State(Scope.Thread)
    public static class MyState {
        public MapInterface.TimedSizableMap<Integer, String> map = new ActorTimedHaspMap<>();
        @Setup(Level.Trial)
        public void doSetup() {
            map = new ActorTimedHaspMap<>();
            for(int i=0;i<ITER_AMOUNT;++i){
                map.put(i, i + "", 10, TimeUnit.SECONDS);
            }
        }
        @TearDown(Level.Trial)
        public void doTearDown() {
            map.terminate();
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
    public void putSingleValue(ActorMapBM.MyState state) {
        state.map.put(10000,10000+"",1,TimeUnit.SECONDS);
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput,Mode.SampleTime,Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    @Threads(value =1)
    @Warmup(iterations = 2)
    @Measurement(iterations = 2)
    @Timeout(time = 20, timeUnit = TimeUnit.SECONDS)
    public Optional<String> getSingleValue(ActorMapBM.MyState state) {
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
    public long getSingleSize(ActorMapBM.MyState state) {
        return state.map.size();
    }

//    @Benchmark
//    @BenchmarkMode({Mode.Throughput,Mode.SampleTime,Mode.AverageTime})
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    @Fork(value = 1)
//    @Threads(value = 4)
//    @Warmup(iterations = 2)
//    @Measurement(iterations = 2)
//    @Timeout(time = 20, timeUnit = TimeUnit.SECONDS)
//    public void putSingleValueMultiThreads(ActorMapBM.MyState state) {
//        state.map.put(20000,20000+"",1,TimeUnit.SECONDS);
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
//    public Optional<String> getSingleValueMultiThreads(ActorMapBM.MyState state) {
//        return state.map.get(20000);
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
//    public long getSingleSizeMultiThreads(ActorMapBM.MyState state) {
//        return state.map.size();
//    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput,Mode.SampleTime,Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    @Threads(value =1)
    @Warmup(iterations = 2)
    @Measurement(iterations = 2)
    @Timeout(time = 20, timeUnit = TimeUnit.SECONDS)
    public void putMultiValues(ActorMapBM.MyState state) {
        for(int i =0; i<ITER_AMOUNT;i++)
            state.map.put(i,i+"",1,TimeUnit.SECONDS);
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput,Mode.SampleTime,Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    @Threads(value =1)
    @Warmup(iterations = 2)
    @Measurement(iterations = 2)
    @Timeout(time = 20, timeUnit = TimeUnit.SECONDS)
    public void getMultiValues(ActorMapBM.MyState state) {
        for(int i =0; i<ITER_AMOUNT;i++)
            Optional.ofNullable(state.map.get(i));
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput,Mode.SampleTime,Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    @Threads(value =1)
    @Warmup(iterations = 2)
    @Measurement(iterations = 2)
    @Timeout(time = 20, timeUnit = TimeUnit.SECONDS)
    public void getMultiSize(ActorMapBM.MyState state) {
        for(int i =0; i<ITER_AMOUNT;i++)
            state.map.size();
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput,Mode.SampleTime,Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    @Threads(value = 4)
    @Warmup(iterations = 2)
    @Measurement(iterations = 2)
    @Timeout(time = 20, timeUnit = TimeUnit.SECONDS)
    public void putMultiValuesMultiThreads(ActorMapBM.MyState state) throws InterruptedException {
         for(int i=0; i<100;i++)
                state.map.put(i,i+"",1,TimeUnit.SECONDS);
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput,Mode.SampleTime,Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    @Threads(value = 4)
    @Warmup(iterations = 2)
    @Measurement(iterations = 2)
    @Timeout(time = 20, timeUnit = TimeUnit.SECONDS)
    public void getMultiValuesMultiThreads(ActorMapBM.MyState state)throws InterruptedException {
            for(int i=0; i<100;i++)
                Optional.ofNullable(state.map.get(i));
    }

    public static void main(String ...args) throws IOException, RunnerException {
        org.openjdk.jmh.Main.main(args);
    }

}
