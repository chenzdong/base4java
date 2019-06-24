package com.czd.thread.threadInit;

/**
 * 实现Runnable接口并重写run方法
 * Thread类本身就是实现了Runnable接口
 *
 * @author: czd
 * @create: 2019/1/14 9:46
 */
public class RunnableThread implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" is running");
    }
    public static void main(String[] args) {
        Thread thread = new Thread(new RunnableThread(), "runnable thread");
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }
}
