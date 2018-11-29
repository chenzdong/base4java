package com.czd.concurrent.testCyclicBarrier;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier测试线程类
 *
 * @author: czd
 * @create: 2018/5/10 14:39
 */
public class Player implements  Runnable{
    private CyclicBarrier barrier;
    private String name;
    public Player(CyclicBarrier barrier,String name){
        this.barrier=barrier;
        this.name=name;
    }

    @Override
    public void run() {
        System.out.println(name + " 准备");
        try {
            barrier.await();
            System.out.println(name + " 完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

    }
}
