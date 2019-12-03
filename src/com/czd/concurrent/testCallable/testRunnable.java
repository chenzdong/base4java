package com.czd.concurrent.testCallable;

/**
 * Runnable接口测试
 * 实现Callable接口的任务线程能返回执行结果；而实现Runnable接口的任务线程不能返回结果；
 * Callable接口的call()方法允许抛出异常；而Runnable接口的run()方法的异常只能在内部消化，不能继续上抛；
 *
 * @author: czd
 * @create: 2018/9/7 9:11
 */
public class testRunnable implements Runnable{
    private String param;

    public testRunnable(String param) {
        this.param = param;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //无法返回 return
        System.out.println("this is my param "+this.param);
    }

    public static void main(String[] args) {
        Runnable runnable = new testRunnable("my runnable test");
        long beginTime = System.currentTimeMillis();
        new Thread(runnable).start();
        //不妨碍主线程
        long endTime = System.currentTimeMillis();
        System.out.println("Cast "+(endTime-beginTime)/1000+" second");
    }
}
