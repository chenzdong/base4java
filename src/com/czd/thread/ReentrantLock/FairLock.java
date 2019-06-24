package com.czd.thread.reentrantLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁
 * ReentrantLock(boolean fair)
 * @author: czd
 * @create: 2018/11/15 14:02
 */
public class FairLock implements Runnable {
    public static ReentrantLock fairLock = new ReentrantLock(true);

    @Override
    public void run() {
        while (true) {
            try {
                fairLock.lock();
                System.out.println(Thread.currentThread().getName()+" get lock");
            } finally {
                fairLock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        FairLock fairLock = new FairLock();
        Thread t1 = new Thread(fairLock,"t1");
        Thread t2 = new Thread(fairLock,"t2");
        t1.start();
        t2.start();
    }
}
