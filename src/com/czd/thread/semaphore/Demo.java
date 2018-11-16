package com.czd.thread.semaphore;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * 信息量semaphore的demo
 * semaphore(int permits, boolean fair) 用一个进一个
 *
 * @author: czd
 * @create: 2018/11/16 9:23
 */
public class Demo {
    public static void main(String[] args) {
//        避免Executors建立线程池，通过ThreadPoolExecutor的方式
//        ExecutorService pool = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(5);
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build();

        //Common Thread Pool
        ExecutorService pool = new ThreadPoolExecutor(5, 200,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 10; i++) {
            pool.execute(()-> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " is getting");
                    Thread.sleep((long)Math.random()*1000L);
                    semaphore.release();
                    System.out.println(Thread.currentThread().getName()+" is losing");
                    System.out.println(semaphore.availablePermits());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        pool.shutdown();

    }
}
