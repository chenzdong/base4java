package com.czd.thread.pool;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


/**
 * 使用ScheduledThreadPoolExecutor建立
 *
 * @author: czd
 * @create: 2018/11/16 9:43
 */
public class UseScheduledThreadPoolExecutor{
    public static void main(String[] args) {
        //org.apache.commons.lang3.concurrent.BasicThreadFactory
        ScheduledExecutorService pool = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("czd-example-pool-%d").daemon(true).build());
        pool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                //deamon 没有输出终端
//                System.out.println(Thread.currentThread().getName());
            }
        }, 1, 1, TimeUnit.SECONDS);
        pool.shutdown();
    }

}
