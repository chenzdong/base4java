package com.czd.thread.group;

/**
 * 线程组测试
 *
 * @author: czd
 * @create: 2018/11/15 9:19
 */
public class ThreadGroupTest {

    public static void main(String[] args) throws InterruptedException{
        ThreadGroup threadGroup1 = new ThreadGroup("tg");
        Thread thread1 = new Thread(threadGroup1,new ThreadInGroup(),"thread1");
        Thread thread2 = new Thread(threadGroup1,new ThreadInGroup(),"thread2");
        System.out.println("begin");
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("all finish");
    }
    public static class ThreadInGroup implements Runnable {
        @Override
        public void run() {
            String threadGroupName = Thread.currentThread().getThreadGroup().getName();
            String threadName = Thread.currentThread().getName();
            System.out.println("group is "+threadGroupName);
            System.out.println("thread name is "+threadName);
            long sleepTime = 2000L;
            if ("thread1".equals(threadName)) {
                sleepTime = 5000L;
            }
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+" is finish");

        }
    }

}
