package com.czd.thread.threadInit;

/**
 * 直接继承Thread类并重写run方法
 *
 * @author: czd
 * @create: 2019/1/14 9:40
 */
public class BaseThread extends Thread{
    BaseThread(String name){
        super(name);
    }
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" is running");
    }

    public static void main(String[] args) {
        BaseThread thread = new BaseThread("myThread");
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
