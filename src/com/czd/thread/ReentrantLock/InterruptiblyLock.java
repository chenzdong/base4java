package com.czd.thread.reentrantLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁的中断响应
 * ReentrantLock的lockInteruptibly() throws InterruptedException
 *
 * @author: czd
 * @create: 2018/11/15 11:17
 */
public class InterruptiblyLock implements Runnable {
    public static ReentrantLock lock1 = new ReentrantLock();
    public static ReentrantLock lock2 = new ReentrantLock();
    int lock;
    public InterruptiblyLock(int lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            if (lock == 1) {
                System.out.println("线程1先开始");
                lock1.lockInterruptibly();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock2.lockInterruptibly();
                System.out.println(Thread.currentThread().getName()+" is finished");
            } else {
                System.out.println("线程2先开始");
                lock2.lockInterruptibly();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock1.lockInterruptibly();
                System.out.println(Thread.currentThread().getName()+" is finished");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock1.isHeldByCurrentThread()) {
                lock1.unlock();
                System.out.println("lock1 is unlock");
            }

            if (lock2.isHeldByCurrentThread()) {
                lock2.unlock();
                System.out.println("lock2 is unlock");
            }
            System.out.println(Thread.currentThread().getName()+" exit");
        }
    }

    public static void main(String[] args) throws InterruptedException{
        InterruptiblyLock lock1 = new InterruptiblyLock(1);
        InterruptiblyLock lock2 = new InterruptiblyLock(2);
        Thread thread1 = new Thread(lock1,"t1");
        Thread thread2 = new Thread(lock2,"t2");
        thread1.start();
        thread2.start();
        Thread.sleep(1000);
        //中断线程2
//        thread2.interrupt();
    }

}
