package com.czd.thread.lockSupport;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport的park方法
 * 相比wait/notify的优势：
 * 1 LockSupport不需要在同步代码块里 。所以线程间也不需要维护一个共享的同步对象了，实现了线程间的解耦。
 * 2 unpark函数可以先于park调用，所以不需要担心线程间的执行的先后顺序。
 * @author: czd
 * @create: 2018/11/19 14:36
 */
public class TestObjPark {
    public static void main(String[] args) throws InterruptedException{
        Thread t1 = new Thread(() -> {
            int sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += i;
            }
            LockSupport.park();
            System.out.println(sum);
        },"T1");
        t1.start();
        //不需要sleep 即使先调用
        Thread.sleep(1000L);
        LockSupport.unpark(t1);
    }
}
