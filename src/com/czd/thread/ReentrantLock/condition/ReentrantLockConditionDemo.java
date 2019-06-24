package com.czd.thread.reentrantLock.condition;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试reentrantLock与conditon结合使用
 * 消费者和生产者
 * @author: czd
 * @create: 2018/11/15 15:57
 */
public class ReentrantLockConditionDemo {
    private ReentrantLock lock = new ReentrantLock();
    private Condition full = lock.newCondition();
    private Condition empty = lock.newCondition();
    private static List<String> cache = new LinkedList<>();

    public void put(String args) throws InterruptedException{
        Thread.sleep(200);
        lock.lock();
        try {
            while (cache.size() != 0) {
                System.out.println("cache is full");
                full.await();
                System.out.println("put begin");
            }
            System.out.println("puting");
            cache.add(args);
            empty.signal();
        } finally {
            lock.unlock();
        }
    }
    public void get() throws InterruptedException {
        try {
            while (!Thread.interrupted()) {
                lock.lock();
                while (cache.size() == 0) {
                    System.out.println("cache is empty");
                    empty.await();
                    System.out.println("get begin");
                }
                System.out.println("getting");
                cache.remove(0);
                full.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final ReentrantLockConditionDemo rd = new ReentrantLockConditionDemo();
        for (int i = 0; i < 1; i++) {
            new Thread(() -> {
                try {
                    rd.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    rd.put("1");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}
