package com.czd.thread.forkAndJoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicLong;

/**
 * fork/join基本使用
 * RecursiveTask有返回 RecursiveAction无返回
 * @author: czd
 * @create: 2018/11/23 14:23
 */
public class Base extends RecursiveTask<Long> {
    private final int DEFAULT_CAPACITY = 10000;
    private int start;
    private int end;
    public Base(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long result = 0L;
        //如果任务量在可负担范围内，直接进行计算
        if (end - start <= DEFAULT_CAPACITY) {
//            System.out.println(Thread.currentThread().getName());
            for (int i = start; i <= end; i++) {
                result += i;
            }
        } else {
            int middle = (start + end) / 2;
            Base base1 = new Base(start, middle);
            Base base2 = new Base(middle+1, end);
            //invokeAll相当于自己干活，其他两个也在干活
//            invokeAll(base1, base2);
            //下列相当于重新分配了两线程，原来的线程就闲置
            base1.fork();
            base2.fork();
            result = base1.join() + base2.join();
        }
        return result;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Base base = new Base(1,100000000);
        long start = System.currentTimeMillis();
        long result = forkJoinPool.invoke(base);
        System.out.println("fork/join 计算结果为: "+result + " 耗时: "+(System.currentTimeMillis()-start));
        long sum = 0L;
        long start1 = System.currentTimeMillis();
        for (int i = 0; i <= 100000000; i++) {
            sum += i;
        }
        System.out.println("普通计算结果为: "+sum+" 耗时为:"+(System.currentTimeMillis()-start1));
    }
}
