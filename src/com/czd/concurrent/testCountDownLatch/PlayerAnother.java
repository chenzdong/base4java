package com.czd.concurrent.testCountDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * countDownLatch测试类，线程为继承Runnable接口生成
 *
 * @author: czd
 * @create: 2018/5/10 9:57
 */
public class PlayerAnother implements Runnable{
    private static int count=1;
    private final int id = count++;
    private CountDownLatch countDownLatch;
    public PlayerAnother(CountDownLatch countDownLatch){
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            System.out.println("【玩家" + id + "】入场" + " thread is " + Thread.currentThread().getName());
        } finally {
            countDownLatch.countDown();
        }
    }

    public static void main(String[] args) throws InterruptedException{
        int count=5;
        CountDownLatch countDownLatch=new CountDownLatch(count);
        System.out.println("等待玩家入场");
        for (int i=0;i<count;i++) {
            new Thread(new PlayerAnother(countDownLatch),String.valueOf(i)).start();
        }
        countDownLatch.await();
        System.out.println("玩家到齐，开始游戏");
    }
}
