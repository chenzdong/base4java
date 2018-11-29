package com.czd.concurrent.testCyclicBarrier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 负责CyclicBarrier初始化
 *
 * @author: czd
 * @create: 2018/5/10 14:42
 */
public class Race {
    public static void main(String[] args) {
        int count=8;
        CyclicBarrier barrier=new CyclicBarrier(count, new Runnable() {
            @Override
            public void run() {
                System.out.println("开始");
            }
        });
        List<Player> players=new ArrayList<>();
        for (int i=0;i<count;i++){
            players.add(new Player(barrier,String.valueOf(i)));
        }
        ExecutorService service= Executors.newFixedThreadPool(count);
        for(Player player:players){
            service.execute(player);
        }
        service.shutdown();
    }
}
