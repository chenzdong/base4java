package com.czd.thread.lockSupport;

/**
 * 测试wait和notify方法与LockSupport的park与unpark进行对比
 *
 * @author: czd
 * @create: 2018/11/19 14:29
 */
public class TestObjWait {
    public static void main(String[] args) throws InterruptedException{
        final Object obj = new Object();
        Thread t1 = new Thread(() -> {
            int sum =0;
            for (int i = 0; i < 10; i++) {
                sum += i;
            }
            try {
                synchronized (obj) {
                    obj.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(sum);
        },"t1");
        t1.start();
        //若注释掉sleep方法 会先调用notify方法，导致线程一直处于waitting状态
        Thread.sleep(1000L);
        synchronized (obj) {
            obj.notify();
        }
    }
}
