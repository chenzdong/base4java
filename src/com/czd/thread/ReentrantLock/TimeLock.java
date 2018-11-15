package com.czd.thread.ReentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 限时等待
 * tryLock() 获取锁 获取不到直接跳过
 *
 * @author: czd
 * @create: 2018/11/15 13:52
 */
public class TimeLock implements Runnable {
    public static ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        try {
            //限时请求，超过限定时间请求失败
            System.out.println(Thread.currentThread().getName()+" is begin");
            if (lock.tryLock(5, TimeUnit.SECONDS)) {
                System.out.println(Thread.currentThread().getName()+" get lock");
                Thread.sleep(6000L);
            } else {
                System.out.println(Thread.currentThread().getName()+" get failed");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                System.out.println(Thread.currentThread().getName()+" release lock");
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        TimeLock lock = new TimeLock();
        Thread thread1 = new Thread(lock,"t1");
        Thread thread2 = new Thread(lock,"t2");
        thread1.start();
        thread2.start();
    }
}
