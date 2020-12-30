package com.czd.thread.test;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : chenzd
 * @create: 2020/12/14 下午10:25
 * 三个线程交替打印输出
 */
public class Solution {
    private final static Semaphore latch1 = new Semaphore(1);
    private final static Semaphore latch2 = new Semaphore(0);
    private final static Semaphore latch3 = new Semaphore(0);
    private static AtomicInteger count = new AtomicInteger(1);
    private final static int PRINT_SIZE = 5;
    private final static int MAX_COUNT = 100;

    public static void printForThread1() {
        while (count.get() < MAX_COUNT - 5) {
            try {
                latch1.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < PRINT_SIZE; i++) {
                System.out.println(Thread.currentThread().getName() + ":" + count.getAndIncrement());
            }
            latch2.release();
        }
    }

    public static void printForThread2() {
        while (count.get() < MAX_COUNT - 5) {
            try {
                latch2.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < PRINT_SIZE; i++) {
                System.out.println(Thread.currentThread().getName() + ":" + count.getAndIncrement());
            }
            latch3.release();
        }
    }

    public static void printForThread3() {
        while (count.get() < MAX_COUNT - 5) {
            try {
                latch3.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < PRINT_SIZE; i++) {
                System.out.println(Thread.currentThread().getName() + ":" + count.getAndIncrement());
            }
            latch1.release();
        }
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            Solution.printForThread1();
        }, "thread1");
        Thread thread2 = new Thread(() -> {
            Solution.printForThread2();
        }, "thread2");
        Thread thread3 = new Thread(() -> {
            Solution.printForThread3();
        }, "thread3");
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
