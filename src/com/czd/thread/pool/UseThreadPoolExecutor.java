package com.czd.thread.pool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * 使用ThreadPoolExecutor
 *
 * @author: czd
 * @create: 2018/11/16 10:25
 */
public class UseThreadPoolExecutor {
    public static void main(String[] args) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build();

        //Common Thread Pool
        ExecutorService pool = new ThreadPoolExecutor(5, 200,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 10; i++) {
            pool.execute(()-> System.out.println(Thread.currentThread().getName()));
        }
        pool.shutdown();//gracefully shutdown
    }

}
