package Naivemap;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Ronen Zolicha
 */

public class NaiveTimedHashMapSlow {
    public static class TimedHashMap<K, V> {

        HashMap<K, V> hashMap=new HashMap<>();
        HashMap<K, ScheduledFuture<?>> removalList = new HashMap<>();
        ScheduledExecutorService scheduled= Executors.newSingleThreadScheduledExecutor();
        Object lock = new Object();

        public int size() {
            return hashMap.size();
        }
        public V get(K key) {
            return hashMap.get(key);
        }

        public void put(K key, V value, long duration, TimeUnit timeUnit) {
            Runnable command;
            synchronized (lock) {
                hashMap.put(key, value);
            }

            synchronized (lock) {
                command = (()->{
                    hashMap.remove(key);
                });
            }

            ScheduledFuture<?> currentRemoval = scheduled.schedule(command, duration, timeUnit);

            if(removalList.containsKey(key)) {
                removalList.get(key).cancel(true);
            }
            removalList.put(key, currentRemoval);
        }
    }
}
