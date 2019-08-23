package com.czd.concurrent.atomic;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * atomicInteger基本使用 (CAS)
 *
 * @author: czd
 * @create: 2018/11/29 9:17
 */
public class AtomicIntegerDemo {
    private static final int THREAD_COUNT = 20;
    //volatile 不能保证原子性
    public static volatile int count = 0;
    public static AtomicInteger num = new AtomicInteger(0);
    public static void increase() {
        count++;
    }
    public static void increase1() {
        num.incrementAndGet();
    }
    public static void main(String[] args) {
        ExecutorService pool = new ThreadPoolExecutor(THREAD_COUNT, THREAD_COUNT, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(),
                new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build(), new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < THREAD_COUNT; i++) {
            pool.execute(() -> {
                for (int j = 0; j < 1000; j++) {
//                    increase();
                    increase1();
                }
            });
        }
        //消耗完线程池内现存的线程
        pool.shutdown();
        while (true) {
            if (pool.isTerminated()) {
                System.out.println("all finished");
//                System.out.println(count);
                System.out.println(num.intValue());
                break;
            }
        }

    }
}
