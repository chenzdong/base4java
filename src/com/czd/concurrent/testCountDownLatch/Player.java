package com.czd.concurrent.testCountDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * countDownLatch测试类 线程为实现Thread生成
 *
 * @author: czd
 * @create: 2018/5/10 9:49
 */
public class Player extends Thread{
    private static int count=1;
    private final int id=count++;
    private CountDownLatch countDownLatch;
    private String name;
    public Player(String name,CountDownLatch countDownLatch){
        this.name=name;
        this.countDownLatch=countDownLatch;
    }

    @Override
    public void run() {
        System.out.println("【玩家"+id+"】入场"+" thread is "+name);
        countDownLatch.countDown();
    }

    public static void main(String[] args) throws InterruptedException{
        int count=5;
        CountDownLatch countDownLatch=new CountDownLatch(count);
        System.out.println("等待玩家入场");
        for (int i=0;i<count;i++) {
            new Player(String.valueOf(i),countDownLatch).start();
        }
        countDownLatch.await();
        System.out.println("玩家到齐，开始游戏");
    }
}
