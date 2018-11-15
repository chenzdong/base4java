package com.czd.thread.baseMethod;

import com.sun.xml.internal.stream.util.ThreadLocalBufferAllocator;

/**
 * 线程基本方法测试
 *
 * @author: czd
 * @create: 2018/11/9 8:51
 */
public class ThreadTest {
    static Object object = new Object();

    public static void main(String[] args) {
        new Thread(new Thread1()).start();
        System.out.println("start is"+System.currentTimeMillis());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("start2 is "+System.currentTimeMillis());
        new Thread(new Thread2()).start();
    }
    private static class Thread1 implements Runnable {
        @Override
        public void run() {
            synchronized (object) {
                long start = System.currentTimeMillis();
                System.out.println("enter thread1");
                System.out.println("thread1 is wating..");
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread1 is over"+(System.currentTimeMillis()-start));
            }
        }
    }

    private static class Thread2 implements Runnable {
        @Override
        public void run() {
            synchronized (object) {
                long start = System.currentTimeMillis();
                System.out.println("enter thread2");
                System.out.println("thread2 is wating..");
                object.notify();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread2 is over "+(System.currentTimeMillis()-start));
            }
        }
    }
}
