package com.czd.thread.threadInit;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 通过实现Callable接口新建线程，重写call()，需配合FutureTask一起使用
 * 实现Callable接口的任务线程能返回执行结果；而实现Runnable接口的任务线程不能返回结果；
 * Callable接口的call()方法允许抛出异常；而Runnable接口的run()方法的异常只能在内部消化，不能继续上抛；
 *
 * @author: czd
 * @create: 2019/1/14 9:58
 */
public class CallableThread implements Callable<String> {
    private String param;
    CallableThread(String param) {
        this.param = param;
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(1000);
        return  this.param+" append some chars and return it";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException{
        Callable<String> callable=new CallableThread("my callable test");
        // 需配合FutureTask一起使用
        FutureTask<String> task=new FutureTask<String>(callable);
        long beginTime=System.currentTimeMillis();
        new Thread(task).start();
        // Callable接口支持返回执行结果，此时需要调用FutureTask.get()方法实现，此方法会阻塞主线程直到获取‘将来’结果；当不调用此方法时，主线程不会阻塞！
        String result=task.get();
        long endTime=System.currentTimeMillis();
        System.out.println("this is result:"+result);
        System.out.println("cast "+(endTime-beginTime)/1000+" second");
    }
}
