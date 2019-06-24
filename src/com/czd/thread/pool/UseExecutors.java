package com.czd.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;

/**
 * 用Executors建立线程池，不推荐
 *1）newFixedThreadPool和newSingleThreadExecutor:
   主要问题是堆积的请求处理队列可能会耗费非常大的内存，甚至OOM。
 *2）newCachedThreadPool和newScheduledThreadPool:
   主要问题是线程数最大数是Integer.MAX_VALUE，可能会创建数量非常多的线程，甚至OOM。
 * @author: czd
 * @create: 2018/11/16 9:35
 */
public class UseExecutors {
    public static void main(String[] args) {

        ExecutorService pool = Executors.newCachedThreadPool();
        for (int i = 0; i < 20; i++) {
            pool.execute(() -> System.out.println(Thread.currentThread().getName()));
        }
        pool.shutdown();
    }
}
