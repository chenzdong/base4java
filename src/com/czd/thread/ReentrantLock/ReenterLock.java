package com.czd.thread.ReentrantLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁的基本特性
 * 同一个线程可多次加锁，但相应释放锁时也需执行多少次
 * lock > unlock 线程堵塞
 * lock < unlock throws java.lang.IllegalMonitorStateException
 * @author: czd
 * @create: 2018/11/15 10:28
 */
public class ReenterLock implements  Runnable {
    public static ReentrantLock lock = new ReentrantLock();
    public static int i = 0;

    @Override
    public void run() {
        for (int j = 0; j < 5; j++) {
            lock.lock();
            System.out.println(Thread.currentThread().getName()+" is lock");
            lock.lock();
            try {
                i++;
            } finally {
                lock.unlock();
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + " is unlock");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException{
        ReenterLock reenterLock = new ReenterLock();
        Thread thread1 = new Thread(reenterLock,"t1");
        Thread thread2 = new Thread(reenterLock,"t2");
        thread1.start();thread2.start();
        thread1.join();thread2.join();
        System.out.println(i);
    }
}
