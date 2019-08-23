package com.czd.thread.readWriteLock;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁Demo
 *
 * @author czd
 * @create 2018-11-17 22:45
 **/
public class ReadWriteLockDemo {
    // 重入锁
    private static ReentrantLock lock = new ReentrantLock();
    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    // 读锁
    private static Lock readLock= reentrantReadWriteLock.readLock();
    // 写锁
    private static Lock writeLock= reentrantReadWriteLock.writeLock();
    private int value;

    public Object handleRead(Lock lock) throws InterruptedException {
        try {
            lock.lock();
            Thread.sleep(1000L);
            return  value;
        }  finally {
            lock.unlock();
        }
    }
    public void handleWrite(Lock lock, int index) throws InterruptedException {
        try {
            lock.lock();
            Thread.sleep(1000L);
            value = index;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(20);
        long start = System.currentTimeMillis();
        final ReadWriteLockDemo demo = new ReadWriteLockDemo();
        Runnable readRunnable = new Runnable() {
            @Override
            public void run() {
               try {
//                  demo.handleRead(readLock);
                   demo.handleRead(lock);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               } finally {
                   countDownLatch.countDown();
               }
            }
        };
        Runnable writeRunnable = new Runnable() {
            @Override
            public void run() {
                try {
//                    demo.handleWrite(writeLock, new Random().nextInt());
                    demo.handleWrite(lock, new Random().nextInt());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            }
        };
        for (int i = 0; i < 18 ; i++) {
            Thread thread = new Thread(readRunnable);
            thread.start();
        }
        for (int i = 0; i < 2; i++) {
            Thread thread = new Thread(writeRunnable);
            thread.start();
        }
        countDownLatch.await();
        // 读写锁耗时 3022 mills 重入锁耗时20009
        System.out.println("use time "+(System.currentTimeMillis()-start));
    }
}

